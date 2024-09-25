package com.example.resizescreenapp.screens.list_detail_pane

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resizescreenapp.navigation.NavigationDestination
import com.example.resizescreenapp.ui.composables.AppBar
import com.example.resizescreenapp.ui.theme.FoldablesDemoTheme
import com.example.resizescreenapp.ui.util.isScreenWidthExpanded
import com.example.resizescreenapp.ui.util.rememberContentForPane
import kotlinx.serialization.Serializable

@Serializable
object ListDetailPaneDestination : NavigationDestination

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPaneScreen(
    navigateBack: () -> Unit,
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val isScreenWidthExpanded = isScreenWidthExpanded()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        defaultBackBehavior = BackNavigationBehavior.PopUntilScaffoldValueChange,
        listPane = {
            AnimatedPane {
                ListContent(
                    onItemClick = { itemIndex ->
                        // Navigate to the detail pane when an item is clicked
                        navigator.navigateTo(
                            pane = ListDetailPaneScaffoldRole.Detail,
                            content = itemIndex
                        )
                    },
                    onBackClick = navigateBack
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val content = rememberContentForPane<Int>(
                    navigator = navigator,
                    pane = ListDetailPaneScaffoldRole.Detail
                )

                ListDetailContent(
                    itemIndex = content,
                    onOptionClick = { optionIndex ->
                        // Navigate to the extra option pane when an option is clicked
                        navigator.navigateTo(
                            pane = ListDetailPaneScaffoldRole.Extra,
                            content = optionIndex
                        )
                    },
                    onBackClick = {
                        val isOptionPaneVisible =
                            navigator.currentDestination?.pane == ListDetailPaneScaffoldRole.Extra

                        val navigationBehavior = when {
                            isOptionPaneVisible -> {
                                // If the option pane is visible, close the option pane
                                BackNavigationBehavior.PopUntilScaffoldValueChange
                            }

                            !isScreenWidthExpanded -> {
                                // If the screen is shown as a separate screen, close the detail pane
                                BackNavigationBehavior.PopUntilScaffoldValueChange
                            }

                            else -> {
                                // Otherwise, pop the detail pane
                                BackNavigationBehavior.PopUntilContentChange
                            }
                        }
                        navigator.navigateBack(navigationBehavior)
                    }
                )
            }
        },
        extraPane = {
            AnimatedPane {
                val content = rememberContentForPane<Int>(
                    navigator = navigator,
                    pane = ListDetailPaneScaffoldRole.Extra,
                )
                OptionContent(
                    optionIndex = content,
                    // Show the top bar only when option screen is shown as a separate screen
                    showTopBar = !isScreenWidthExpanded,
                    onBackClick = {
                        // Close the option pane
                        navigator.navigateBack(BackNavigationBehavior.PopUntilScaffoldValueChange)
                    }
                )
            }
        }
    )
}

@Composable
fun ListContent(
    onItemClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "ListDetailPane Demo",
                onBackClick = onBackClick,
            )
        }
    ) { pv ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(100) {
                Text(
                    text = "Item $it",
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .clickable { onItemClick(it) }
                        .padding(vertical = 16.dp, horizontal = 20.dp)
                )
            }
        }
    }
}

@Composable
fun ListDetailContent(
    itemIndex: Int?,
    onOptionClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = if (itemIndex != null) "Item $itemIndex" else "Details",
                onBackClick = if (itemIndex != null) onBackClick else null,
            )
        }
    ) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(pv),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = if (itemIndex != null) "Item $itemIndex" else "Select an option")

            if (itemIndex != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    repeat(2) {
                        AssistChip(
                            onClick = { onOptionClick(it) },
                            label = {
                                Text(text = "Option $it")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OptionContent(
    optionIndex: Int?,
    showTopBar: Boolean = false,
    onBackClick: (() -> Unit)? = null,
) {
    Scaffold(
        topBar = {
            if (showTopBar) {
                AppBar(
                    title = "Option $optionIndex",
                    onBackClick = onBackClick,
                )
            }
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .padding(pv),
            contentAlignment = Alignment.Center
        ) {
            Text(text = if (optionIndex != null) "Option $optionIndex" else "Select an option")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FoldablesDemoTheme {
        ListDetailPaneScreen {}
    }
}