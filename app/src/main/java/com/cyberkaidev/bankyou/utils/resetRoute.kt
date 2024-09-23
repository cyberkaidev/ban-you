package com.cyberkaidev.bankyou.utils

import androidx.navigation.NavHostController

fun resetRoute(navController: NavHostController, route: String) {
    return navController.navigate(route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}