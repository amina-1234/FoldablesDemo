package com.example.resizescreenapp.screens.supporting_pane

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.NavigableSupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resizescreenapp.navigation.NavigationDestination
import com.example.resizescreenapp.ui.composables.AppBar
import com.example.resizescreenapp.ui.theme.FoldablesDemoTheme
import com.example.resizescreenapp.ui.util.isScreenWidthExpanded
import com.example.resizescreenapp.ui.util.rememberContentForPane
import kotlinx.serialization.Serializable

@Serializable
object SupportingPaneDestination : NavigationDestination

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SupportingPaneScreen(
    navigateBack: () -> Unit,
) {
    /**
     * Experiment with different settings to achieve various layouts,
     * control screen partitioning, and customize how the screen is split.
     */
    val navigator = rememberSupportingPaneScaffoldNavigator<Any>(
//        scaffoldDirective = calculatePaneScaffoldDirective(currentWindowAdaptiveInfo())
//
//        scaffoldDirective = PaneScaffoldDirective.Default
//
//        scaffoldDirective = PaneScaffoldDirective(
//            maxHorizontalPartitions = 3,
//            horizontalPartitionSpacerSize = 0.dp,
//            maxVerticalPartitions = 0,
//            verticalPartitionSpacerSize = 0.dp,
//            defaultPanePreferredWidth = 360.dp,
//            excludedBounds = emptyList()
//        )
//
    )


    NavigableSupportingPaneScaffold(
        navigator = navigator,
        defaultBackBehavior = BackNavigationBehavior.PopUntilScaffoldValueChange,
        mainPane = {
            MainContent(
                showSupportingPaneButton = !isScreenWidthExpanded() || navigator.scaffoldDirective.maxHorizontalPartitions == 1,
                onShowSupportingClick = {
                    // Navigate to the supporting pane when the button is clicked
                    navigator.navigateTo(
                        pane = SupportingPaneScaffoldRole.Supporting,
                    )
                },
                onBackClick = navigateBack
            )
        },
        supportingPane = {
            SupportingContent(
                onItemClick = { itemIndex ->
                    // Navigate to the extra option pane when an option is clicked
                    navigator.navigateTo(
                        pane = SupportingPaneScaffoldRole.Extra,
                        content = itemIndex
                    )
                },
                showBackButton = !isScreenWidthExpanded() || navigator.scaffoldDirective.maxHorizontalPartitions == 1,
                onBackClick = {
                    navigator.navigateBack()
                }
            )
        },
        extraPane = {
            val content = rememberContentForPane<Int>(
                navigator = navigator,
                pane = SupportingPaneScaffoldRole.Extra,
            )
            ExtraContent(
                itemIndex = content,
                onBackClick = {
                    // Close the extra pane
                    navigator.navigateBack(BackNavigationBehavior.PopUntilScaffoldValueChange)
                }
            )
        }
    )
}

@Composable
private fun MainContent(
    showSupportingPaneButton: Boolean,
    onShowSupportingClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "SupportingPane Demo",
                onBackClick = onBackClick,
                actions = {
                    if (showSupportingPaneButton) {
                        IconButton(onClick = onShowSupportingClick) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(pv),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Main Content")
        }
    }
}

@Composable
private fun SupportingContent(
    showBackButton: Boolean,
    onItemClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            if (showBackButton) {
                AppBar(
                    title = "Supporting Pane",
                    onBackClick = onBackClick,
                )
            }
        }
    ) { pv ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(20) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .clickable { onItemClick(it) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Item $it")
                }
            }
        }
    }
}

@Composable
private fun ExtraContent(
    itemIndex: Int?,
    onBackClick: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        topBar = {
            AppBar(
                title = if (itemIndex != null) "Item $itemIndex" else "Details",
                onBackClick = onBackClick,
            )
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = if (itemIndex != null) "Item $itemIndex" else "Select an item")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FoldablesDemoTheme {
        SupportingPaneScreen {}
    }
}
