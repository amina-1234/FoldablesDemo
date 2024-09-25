package com.example.resizescreenapp.screens.navigation_suite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.resizescreenapp.navigation.NavigationDestination
import com.example.resizescreenapp.ui.composables.AppBar
import com.example.resizescreenapp.ui.theme.FoldablesDemoTheme
import kotlinx.serialization.Serializable

@Serializable
object NavigationSuiteDestination : NavigationDestination

@Composable
fun NavigationSuiteScreen(
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Navigation Suite Demo",
                onBackClick = navigateBack
            )
        }
    ) { pv ->
        var selectedItemIndex by remember {
            mutableIntStateOf(0)
        }
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                Screen.entries.forEachIndexed { index, screen ->
                    item(
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                        },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = {
                            Text(text = screen.title)
                        }
                    )
                }
            },
            /**
             * Calculates the appropriate [NavigationSuiteType] based on the screen posture and size:
             * Expanded or wide screen -> [NavigationSuiteType.NavigationRail]
             * Collapsed or compact screen -> [NavigationSuiteType.NavigationBar]
             */
            layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                currentWindowAdaptiveInfo()
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Screen.entries.forEachIndexed { index, screen ->
                    if (selectedItemIndex == index) {
                        Text(text = Screen.entries[index].title)
                    }
                }
            }
        }
    }
}

enum class Screen(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    SEARCH("Search", Icons.Default.Search),
    SETTINGS("Settings", Icons.Default.Settings),
}

@Preview
@Composable
private fun Preview() {
    FoldablesDemoTheme {
        NavigationSuiteScreen {}
    }
}