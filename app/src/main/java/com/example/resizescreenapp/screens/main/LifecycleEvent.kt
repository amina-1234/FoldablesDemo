package com.example.resizescreenapp.screens.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.staticCompositionLocalOf

enum class LifecycleEvent { ON_CREATE, ON_START, ON_RESUME, ON_PAUSE, ON_STOP, ON_DESTROY }

val LocalLifecycleEvents = staticCompositionLocalOf<SnapshotStateList<LifecycleEvent>> {
    mutableStateListOf()
}