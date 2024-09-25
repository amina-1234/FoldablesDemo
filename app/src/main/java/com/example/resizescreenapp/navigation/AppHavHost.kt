package com.example.resizescreenapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resizescreenapp.screens.expandable_content.ExpandableContentDestination
import com.example.resizescreenapp.screens.expandable_content.ExpandableContentScreen
import com.example.resizescreenapp.screens.lifecycle_demo.LifecycleDemoDestination
import com.example.resizescreenapp.screens.lifecycle_demo.LifecycleDemoScreen
import com.example.resizescreenapp.screens.list_detail_pane.ListDetailPaneDestination
import com.example.resizescreenapp.screens.list_detail_pane.ListDetailPaneScreen
import com.example.resizescreenapp.screens.main.MainDestination
import com.example.resizescreenapp.screens.main.MainScreen
import com.example.resizescreenapp.screens.navigation_suite.NavigationSuiteDestination
import com.example.resizescreenapp.screens.navigation_suite.NavigationSuiteScreen
import com.example.resizescreenapp.screens.supporting_pane.SupportingPaneDestination
import com.example.resizescreenapp.screens.supporting_pane.SupportingPaneScreen

@Composable
fun AppHavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: NavigationDestination,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable<MainDestination> {
            MainScreen(
                navigateToLifecycleDemo = {
                    navController.navigate(LifecycleDemoDestination)
                },
                navigateToExpandableContent = {
                    navController.navigate(ExpandableContentDestination)
                },
                navigateToListDetailPane = {
                    navController.navigate(ListDetailPaneDestination)
                },
                navigateToSupportingPane = {
                    navController.navigate(SupportingPaneDestination)
                },
                navigateToNavigationSuite = {
                    navController.navigate(NavigationSuiteDestination)
                }
            )
        }

        composable<LifecycleDemoDestination> {
            LifecycleDemoScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable<ExpandableContentDestination> {
            ExpandableContentScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<ListDetailPaneDestination> {
            ListDetailPaneScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<SupportingPaneDestination> {
            SupportingPaneScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<NavigationSuiteDestination> {
            NavigationSuiteScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}