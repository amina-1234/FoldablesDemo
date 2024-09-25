package com.example.resizescreenapp.screens.lifecycle_demo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resizescreenapp.navigation.NavigationDestination
import com.example.resizescreenapp.screens.main.LifecycleEvent
import com.example.resizescreenapp.screens.main.LocalLifecycleEvents
import com.example.resizescreenapp.ui.composables.AppBar
import com.example.resizescreenapp.ui.theme.FoldablesDemoTheme
import kotlinx.serialization.Serializable

@Serializable
object LifecycleDemoDestination : NavigationDestination

@Composable
fun LifecycleDemoScreen(
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Activity Lifecycle Demo",
                onBackClick = navigateBack
            )
        }
    ) { padding ->
        val events = LocalLifecycleEvents.current

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(events) { event ->
                Text(
                    text = event.name,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                )
                if (event == LifecycleEvent.ON_DESTROY) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "\uD83D\uDD04"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FoldablesDemoTheme {
        LifecycleDemoScreen {}
    }
}