package com.appat.connectioncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.appat.connectioncompose.navigation.NavigationGraph
import com.appat.connectioncompose.ui.theme.ConnectionComposeTheme
import com.appat.reachability.setNetworkListenerContent

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNetworkListenerContent {
            ConnectionComposeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(title = {
                                Text("PhotoGrid")
                            })
                        }
                    ) { contentPadding ->
                        MainActivityContent(contentPadding)
                    }
                }
            }
        }
    }
}

@Composable
fun MainActivityContent(contentPadding: PaddingValues) {
    val navController = rememberNavController()
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding)){
        NavigationGraph(navController = navController)
    }
}