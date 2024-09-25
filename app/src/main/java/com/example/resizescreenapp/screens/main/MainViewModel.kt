package com.example.resizescreenapp.screens.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _lifecycleEvents = mutableStateListOf<LifecycleEvent>()
    val lifecycleEvents: SnapshotStateList<LifecycleEvent> get() = _lifecycleEvents

    val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            addLifecycleEvent(LifecycleEvent.ON_CREATE)
        }

        override fun onStart(owner: LifecycleOwner) {
            addLifecycleEvent(LifecycleEvent.ON_START)
        }

        override fun onResume(owner: LifecycleOwner) {
            addLifecycleEvent(LifecycleEvent.ON_RESUME)
        }

        override fun onPause(owner: LifecycleOwner) {
            addLifecycleEvent(LifecycleEvent.ON_PAUSE)
        }

        override fun onStop(owner: LifecycleOwner) {
            addLifecycleEvent(LifecycleEvent.ON_STOP)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            addLifecycleEvent(LifecycleEvent.ON_DESTROY)
        }
    }

    private fun addLifecycleEvent(event: LifecycleEvent) {
        _lifecycleEvents.add(event)
    }
}