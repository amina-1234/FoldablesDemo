package com.example.resizescreenapp.ui.util

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * @return the content of the current destination only if it matches the specified pane.
 * If the current destination's pane does not match, the previously stored content is retained.
 */
@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
inline fun <reified T> rememberContentForPane(
    navigator: ThreePaneScaffoldNavigator<*>,
    pane: ThreePaneScaffoldRole
): T? {
    var content by remember { mutableStateOf<T?>(null) }

    if (navigator.currentDestination?.pane == pane) {
        content = navigator.currentDestination?.content as? T
    }

    return content
}