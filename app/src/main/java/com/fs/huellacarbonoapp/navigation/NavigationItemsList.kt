package com.fs.huellacarbonoapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SpaceDashboard
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SpaceDashboard
import androidx.compose.ui.graphics.vector.ImageVector
import com.fs.huellacarbonoapp.R

data class NavigationItem(
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val titleIdRes: Int,
    val route: String
)

val navigationItemsList = listOf(
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        titleIdRes = R.string.home_label,
        route = Route.Home.route,
    ),
    NavigationItem(
        unSelectedIcon = Icons.Outlined.SpaceDashboard,
        selectedIcon = Icons.Filled.SpaceDashboard,
        titleIdRes = R.string.dashboard_label,
        route = Route.Dashboard.route
    )
)
