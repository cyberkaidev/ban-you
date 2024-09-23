package com.cyberkaidev.bankyou.ui.view.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme
import com.cyberkaidev.bankyou.utils.resetRoute
import com.cyberkaidev.bankyou.viewmodel.UserViewModel

@Composable
fun InitialPage(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val address by userViewModel.address.collectAsState(initial = false)

    LaunchedEffect(address) {
        if (address == false) return@LaunchedEffect
        if (address.toString().isBlank()) {
            resetRoute(navController, "WelcomePage")
        } else {
            resetRoute(navController, "HomePage/false")
        }
    }

    BankYouTheme {
        Scaffold { innerPadding ->
            Surface(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {}
        }
    }
}