package com.cyberkaidev.bankyou.ui.view.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme
import com.cyberkaidev.bankyou.viewmodel.BalanceViewModel
import com.cyberkaidev.bankyou.viewmodel.TransactionsViewModel
import com.cyberkaidev.bankyou.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun InitialPage(
    navController: NavHostController,
    userViewModel: UserViewModel,
    balanceViewModel: BalanceViewModel,
    transactionsViewModel: TransactionsViewModel,
) {
    val address by userViewModel.address.collectAsState(initial = false)
    val scope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }

    fun navigate(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun getBalanceAndTransactions(value: String) {
        scope.launch {
            try {
                balanceViewModel.getBalance(value)
                transactionsViewModel.getTransactions(value)
                navigate("HomePage")
            } catch (error: Throwable) {
                navigate("WelcomePage")
            }
        }
    }

    LaunchedEffect(address) {
        if (address == false) return@LaunchedEffect
        if (address.toString().isBlank()) {
            navigate("WelcomePage")
        } else {
            isLoading.value = true
            getBalanceAndTransactions(address.toString())
        }
    }

    BankYouTheme {
        Scaffold { innerPadding ->
            Surface(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isLoading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(50.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                }
            }
        }
    }
}