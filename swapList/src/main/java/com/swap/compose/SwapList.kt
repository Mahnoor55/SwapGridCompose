package com.swap.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun DragSwapRow(
    listSize: Int,
    boxSize: Dp,
    onSwap: (fromIndex: Int, toIndex: Int) -> Unit,
    content: @Composable (index: Int) -> Unit
) {
    val boxSizePx = with(LocalDensity.current) { boxSize.toPx() }
    var draggedIndex by remember { mutableIntStateOf(-1) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        repeat(listSize) { index ->
            val isDragging = index == draggedIndex

            Box(modifier = Modifier
                .padding(2.dp)
                .size(boxSize)
                .graphicsLayer {
                    if (isDragging) {
                        translationX = dragOffset.x
                        translationY = dragOffset.y
                        scaleX = 1.1f
                        scaleY = 1.1f
                    }
                }
                .zIndex(if (isDragging) 1f else 0f)
                .pointerInput(index) {
                    detectDragGestures(onDragStart = {
                        draggedIndex = index
                    }, onDrag = { change, dragAmount ->
                        change.consume()
                        dragOffset += Offset(dragAmount.x, dragAmount.y)
                    }, onDragEnd = {
                        val finalX = (index * boxSizePx) + dragOffset.x + boxSizePx / 2
                        val finalY = boxSizePx / 2 + dragOffset.y

                        val targetIndex = (0 until listSize).firstOrNull { i ->
                            val startX = i * boxSizePx
                            val endX = startX + boxSizePx
                            finalX in startX..endX && finalY in 0f..boxSizePx
                        }

                        if (targetIndex != null && draggedIndex != -1 && targetIndex != draggedIndex) {
                            onSwap(draggedIndex, targetIndex)
                        }

                        dragOffset = Offset.Zero
                        draggedIndex = -1
                    }

                    )
                }, contentAlignment = Alignment.Center
            ) {
                content(index)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DragSwapGrid(
    listSize: Int,
    columns: Int,
    boxSize: Dp,
    onSwap: (fromIndex: Int, toIndex: Int) -> Unit,
    content: @Composable (index: Int) -> Unit
) {
    val boxSizePx = with(LocalDensity.current) { boxSize.toPx() }
    var draggedIndex by remember { mutableIntStateOf(-1) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    val itemRects = remember { MutableList<Rect?>(listSize) { null } }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        userScrollEnabled = false
    ) {
        items(listSize) { index ->
            val isDragging = index == draggedIndex

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(boxSize)
                    .onGloballyPositioned { coords ->
                        val rect = coords.boundsInWindow()
                        itemRects[index] = rect
                    }
                    .graphicsLayer {
                        if (isDragging) {
                            translationX = dragOffset.x
                            translationY = dragOffset.y
                            scaleX = 1.05f
                            scaleY = 1.05f
                        }
                    }
                    .zIndex(if (isDragging) 1f else 0f)
                    .pointerInput(index) {
                        detectDragGestures(
                            onDragStart = {
                                draggedIndex = index
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragOffset += dragAmount
                            },
                            onDragEnd = {
                                val draggedRect = itemRects.getOrNull(draggedIndex)
                                val draggedCenter = draggedRect?.center?.plus(dragOffset)

                                val targetIndex = draggedCenter?.let { center ->
                                    itemRects.indexOfFirst { rect ->
                                        rect?.contains(center) == true
                                    }
                                } ?: -1

                                if (
                                    targetIndex != -1 &&
                                    draggedIndex != -1 &&
                                    targetIndex != draggedIndex
                                ) {
                                    onSwap(draggedIndex, targetIndex)
                                }

                                dragOffset = Offset.Zero
                                draggedIndex = -1
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                content(index)
            }
        }
    }
}




@Composable
fun DragSwapColumn(
    listSize: Int,
    boxSize: Dp,
    onSwap: (fromIndex: Int, toIndex: Int) -> Unit,
    content: @Composable (index: Int) -> Unit
) {
    val boxSizePx = with(LocalDensity.current) { boxSize.toPx() }
    var draggedIndex by remember { mutableIntStateOf(-1) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        repeat(listSize) { index ->
            val isDragging = index == draggedIndex

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(boxSize)
                    .graphicsLayer {
                        if (isDragging) {
                            translationY = dragOffset.y
                            translationX = dragOffset.x
                            scaleX = 1.1f
                            scaleY = 1.1f
                        }
                    }
                    .zIndex(if (isDragging) 1f else 0f)
                    .pointerInput(index) {
                        detectDragGestures(
                            onDragStart = {
                                draggedIndex = index
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragOffset += Offset(dragAmount.x, dragAmount.y)
                            },
                            onDragEnd = {
                                val finalY = (index * boxSizePx) + dragOffset.y + boxSizePx / 2
                                val finalX = boxSizePx / 2 + dragOffset.x

                                val targetIndex = (0 until listSize).firstOrNull { i ->
                                    val startY = i * boxSizePx
                                    val endY = startY + boxSizePx
                                    finalY in startY..endY && finalX in 0f..boxSizePx
                                }

                                if (targetIndex != null && draggedIndex != -1 && targetIndex != draggedIndex) {
                                    onSwap(draggedIndex, targetIndex)
                                }

                                dragOffset = Offset.Zero
                                draggedIndex = -1
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                content(index)
            }
        }
    }
}
