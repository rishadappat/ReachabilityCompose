package com.appat.reachability

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView

class ReachabilityMessage(context: Context) : AbstractComposeView(context) {
    @Composable
    override fun Content() {
        ReachabilityStatusBox(modifier = Modifier.fillMaxWidth(), isConnected = true)
    }
}