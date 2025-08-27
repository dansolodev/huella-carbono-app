package com.fs.huellacarbonoapp.navigation

sealed class Route(val route: String) {
    data object Home : Route(route = "home")
    data object Dashboard : Route(route = "dashboard")
}
