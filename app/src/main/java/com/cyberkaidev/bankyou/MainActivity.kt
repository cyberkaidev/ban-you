package com.cyberkaidev.bankyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                        composable(
                            route = "HomePage/{wasItLoaded}",
                            arguments = listOf(navArgument("wasItLoaded") {
                                type = NavType.BoolType
                            })
                        ) { backStackEntry ->
                            HomePage(
                                wasItLoaded = backStackEntry.arguments?.getBoolean("wasItLoaded"),
                                navController,
                                userViewModel,
                                balanceViewModel,
                                transactionsViewModel
                            )
                        }
                        composable(
                            route = "SettingsPage",
                            enterTransition = {
                                return@composable slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Start,
                                    tween(500)
                                )
                            },
                            popExitTransition = {
                                return@composable slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.End,
                                    tween(500)
                                )
                            }
                        ) {
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