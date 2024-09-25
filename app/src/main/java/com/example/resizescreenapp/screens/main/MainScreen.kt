package com.example.resizescreenapp.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resizescreenapp.navigation.NavigationDestination
import com.example.resizescreenapp.ui.theme.FoldablesDemoTheme
import kotlinx.serialization.Serializable

@Serializable
object MainDestination : NavigationDestination

@Composable
fun MainScreen(
    navigateToLifecycleDemo: () -> Unit,
    navigateToExpandableContent: () -> Unit,
    navigateToListDetailPane: () -> Unit,
    navigateToSupportingPane: () -> Unit,
    navigateToNavigationSuite: () -> Unit,
) {
    Scaffold { pv ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(onClick = { navigateToLifecycleDemo() }) {
                    Text("1. Activity Lifecycle Demo")
                }
                Button(onClick = { navigateToExpandableContent() }) {
                    Text("2. Expandable Content")
                }
                Button(onClick = { navigateToListDetailPane() }) {
                    Text("3. List Detail Pane")
                }
                Button(onClick = { navigateToSupportingPane() }) {
                    Text("4. Supporting Pane")
                }
                Button(onClick = { navigateToNavigationSuite() }) {
                    Text("5. Navigation Suite")
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FoldablesDemoTheme {
        MainScreen(
            navigateToLifecycleDemo = {},
            navigateToExpandableContent = {},
            navigateToListDetailPane = {},
            navigateToSupportingPane = {},
            navigateToNavigationSuite = {},
        )
    }
}