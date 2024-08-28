package com.cyberkaidev.bankyou

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme
import com.cyberkaidev.bankyou.ui.view.pages.HomePage
import com.cyberkaidev.bankyou.ui.view.pages.InitialPage
import com.cyberkaidev.bankyou.ui.view.pages.RegisterPage
import com.cyberkaidev.bankyou.ui.view.pages.SettingsPage
import com.cyberkaidev.bankyou.ui.view.pages.TermsPage
import com.cyberkaidev.bankyou.ui.view.pages.WelcomePage
import com.cyberkaidev.bankyou.viewmodel.BalanceViewModel
import com.cyberkaidev.bankyou.viewmodel.TransactionsViewModel
import com.cyberkaidev.bankyou.viewmodel.UserViewModel
import com.google.android.material.color.DynamicColors
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        DynamicColors.applyToActivitiesIfAvailable(this.application)
        setContent {
            BankYouTheme {
                Surface {
                    val navController = rememberNavController()
                    val userViewModel = koinViewModel<UserViewModel>()
                    val balanceViewModel = koinViewModel<BalanceViewModel>()
                    val transactionsViewModel = koinViewModel<TransactionsViewModel>()

                    NavHost(navController = navController, startDestination = "InitialPage") {
                        composable("InitialPage") {
                            InitialPage(
                                navController,
                                userViewModel,
                                balanceViewModel,
                                transactionsViewModel,
                            )
                        }
                        composable("WelcomePage") {
                            WelcomePage(navController)
                        }
                        composable("RegisterPage") {
                            RegisterPage(
                                navController,
                                userViewModel,
                                balanceViewModel,
                                transactionsViewModel,
                            )
                        }
                        composable("HomePage") {
                            HomePage(
                                navController,
                                balanceViewModel,
                                transactionsViewModel
                            )
                        }
                        composable("SettingsPage") {
                            SettingsPage(
                                navController,
                                userViewModel
                            )
                        }
                        composable("TermsPage") {
                            TermsPage(navController)
                        }
                    }
                }
            }
        }
    }
}