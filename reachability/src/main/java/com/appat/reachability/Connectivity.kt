package com.appat.reachability

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appat.connectivity.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

sealed class ReachabilityState {
    object Available : ReachabilityState()
    object Unavailable : ReachabilityState()
}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ReachabilityStatus(modifier: Modifier = Modifier) {
    val connection by reachabilityState()
    var visibility by remember { mutableStateOf(false) }
    val isConnected = connection == ReachabilityState.Available
    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        ReachabilityStatusBox(modifier = modifier, isConnected = isConnected)
    }

    LaunchedEffect(isConnected) {
        visibility = if (!isConnected) {
            true
        } else {
            delay(2000)
            false
        }
    }
}

@Composable
internal fun ReachabilityStatusBox(isConnected: Boolean,
                                   modifier: Modifier = Modifier,
                                   networkAvailableColor: Color = networkAvailable,
                                   networkUnAvailableColor: Color = networkUnAvailable,
                                   @StringRes connectedText: Int = R.string.status_online,
                                   @StringRes disconnectedText: Int = R.string.no_internet,
                                   @DrawableRes connectedDrawable: Int = R.drawable.ic_connectivity_available,
                                   @DrawableRes disconnectedDrawable: Int = R.drawable.ic_connectivity_unavailable) {
    val backgroundColor by animateColorAsState(if (isConnected) networkAvailableColor else networkUnAvailableColor,
        label = "backgroundColor"
    )
    val message = if (isConnected)
        stringResource(id = connectedText)
    else
        stringResource(id = disconnectedText)
    val iconResource = if (isConnected) {
        connectedDrawable
    } else {
        disconnectedDrawable
    }
    Box(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(8.dp)
            .statusBarsPadding(), Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(id = iconResource), "Connectivity Icon", tint = Color.White)
            Spacer(modifier = Modifier.size(8.dp))
            Text(message, color = Color.White, fontSize = 15.sp)
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun reachabilityState(): State<ReachabilityState> {
    val context = LocalContext.current

    // Creates a State<ConnectionState> with current connectivity state as initial value
    return produceState(initialValue = context.currentConnectivityState) {
        // In a coroutine, can make suspend calls
        context.observeReachabilityAsFlow().collect { value = it }
    }
}


fun ComponentActivity.setNetworkListenerContent(content: @Composable () -> Unit) {
    setContent {
        Column(modifier = Modifier.systemBarsPadding()) {
            ReachabilityStatus()
            content()
        }
    }
}

val Context.currentConnectivityState: ReachabilityState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentReachabilityState(connectivityManager)
    }

private fun getCurrentReachabilityState(
    connectivityManager: ConnectivityManager
): ReachabilityState {
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    val connected = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
            || capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            || capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true
    return if (connected) ReachabilityState.Available else ReachabilityState.Unavailable
}

@ExperimentalCoroutinesApi
fun Context.observeReachabilityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState ->
        if(connectionState == ReachabilityState.Unavailable) {
            this.launch {
                delay(1000)
                trySend(currentConnectivityState)
            }
        }
        else {
            this.launch {
                delay(1000)
                trySend(currentConnectivityState)
            }
        }
    }

    val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    // Set current state
    val currentState = getCurrentReachabilityState(connectivityManager)
    trySend(currentState)

    // Remove callback when not used
    awaitClose {
        // Remove listeners
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

fun networkCallback(callback: (ReachabilityState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.d("onAvailable", network.toString())
            callback(ReachabilityState.Available)
        }

        override fun onLost(network: Network) {
            Log.d("onLost", network.toString())
            callback(ReachabilityState.Unavailable)
        }
    }
}