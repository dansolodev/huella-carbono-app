package com.fs.huellacarbonoapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fs.huellacarbonoapp.navigation.BottomNavigationBar
import com.fs.huellacarbonoapp.navigation.Route
import com.fs.huellacarbonoapp.navigation.navigationItemsList
import com.fs.huellacarbonoapp.ui.screens.DashboardScreen
import com.fs.huellacarbonoapp.ui.screens.HomeScreen
import com.fs.huellacarbonoapp.ui.theme.HuellaCarbonoAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HuellaCarbonoAppTheme {
                var routeSelected by remember { mutableStateOf(Route.Home.route) }
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = navigationItemsList,
                            currentRoute = routeSelected,
                            onNavigationItemSelected = {
                                routeSelected = it.route
                                navController.navigate(route = routeSelected) {
                                    popUpTo(route = navController.graph.startDestinationRoute.orEmpty()) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) {
                    NavHost(navController = navController, startDestination = Route.Home.route) {
                        composable(route = Route.Home.route) {
                            HomeScreen()
                        }
                        composable(route = Route.Dashboard.route) {
                            DashboardScreen()
                        }
                    }
                }
            }
        }
    }
}
