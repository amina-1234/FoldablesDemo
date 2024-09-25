package com.example.resizescreenapp.screens.expandable_content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resizescreenapp.navigation.NavigationDestination
import com.example.resizescreenapp.ui.composables.AppBar
import com.example.resizescreenapp.ui.theme.FoldablesDemoTheme
import com.example.resizescreenapp.ui.util.isScreenWidthExpanded
import kotlinx.serialization.Serializable

@Serializable
object ExpandableContentDestination : NavigationDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableContentScreen(
    navigateBack: () -> Unit,
) {
    val isScreenWidthExpanded = isScreenWidthExpanded()
    var selectedItemIndex: Int? by remember {
        mutableStateOf(null)
    }

    // Calculate the number of columns depending on the screen size
    // and whether a separate space is needed for the selected item
    val columnsCount = remember(isScreenWidthExpanded, selectedItemIndex) {
        when {
            isScreenWidthExpanded && selectedItemIndex == null -> 4
            isScreenWidthExpanded -> 2
            else -> 2
        }
    }
    if (isScreenWidthExpanded()) {
        // If the screen is expanded, show item details to the right of the list
        Scaffold(
            topBar = {
                AppBar(
                    title = "Expandable Content",
                    onBackClick = navigateBack
                )
            }
        ) { pv ->
            Row(modifier = Modifier.padding(pv)) {
                ListContent(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    columnsCount = columnsCount,
                    onItemClick = { itemIndex ->
                        selectedItemIndex = if (selectedItemIndex == itemIndex) null else itemIndex
                    }
                )
                selectedItemIndex?.let { itemIndex ->
                    ItemDetailsContent(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(16.dp),
                        itemIndex = itemIndex,
                    )
                }
            }
        }
    } else {
        // If the screen is collapsed, use BottomSheet to display the note details
        val bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )

        LaunchedEffect(selectedItemIndex) {
            if (selectedItemIndex != null) {
                bottomSheetState.expand()
            } else {
                bottomSheetState.hide()
            }
        }

        LaunchedEffect(bottomSheetState.isVisible) {
            if (!bottomSheetState.isVisible) {
                selectedItemIndex = null
            }
        }

        BottomSheetScaffold(
            topBar = {
                AppBar(
                    title = "Expandable Content",
                    onBackClick = navigateBack
                )
            },
            sheetContent = {
                selectedItemIndex?.let { itemIndex ->
                    ItemDetailsContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)
                            .padding(16.dp),
                        itemIndex = itemIndex,
                    )
                }
            },
            sheetPeekHeight = 0.dp,
            scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = bottomSheetState
            )
        ) { pv ->
            ListContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pv),
                columnsCount = columnsCount,
                onItemClick = { itemIndex ->
                    selectedItemIndex = if (selectedItemIndex == itemIndex) null else itemIndex
                }
            )
        }
    }
}

@Composable
private fun ListContent(
    modifier: Modifier = Modifier,
    columnsCount: Int,
    onItemClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnsCount),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(20) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable { onItemClick(it) },
                contentAlignment = Alignment.Center,
            ) {
                Text("Item $it")
            }
        }
    }
}

@Composable
private fun ItemDetailsContent(
    modifier: Modifier = Modifier,
    itemIndex: Int,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        Text("Item $itemIndex")
    }
}

@Preview
@Composable
private fun Preview() {
    FoldablesDemoTheme {
        ExpandableContentScreen {}
    }
}