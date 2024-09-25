package com.example.resizescreenapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import com.example.resizescreenapp.navigation.AppHavHost
import com.example.resizescreenapp.screens.main.LocalLifecycleEvents
import com.example.resizescreenapp.screens.main.MainDestination
import com.example.resizescreenapp.screens.main.MainViewModel
import com.example.resizescreenapp.ui.theme.FoldablesDemoTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycle.addObserver(mainViewModel.lifecycleObserver)

        setContent {
            CompositionLocalProvider(
                LocalLifecycleEvents provides mainViewModel.lifecycleEvents
            ) {
                FoldablesDemoTheme {
                    AppHavHost(startDestination = MainDestination)
                }
            }
        }
    }

    override fun onDestroy() {
        lifecycle.removeObserver(mainViewModel.lifecycleObserver)
        super.onDestroy()
    }
}