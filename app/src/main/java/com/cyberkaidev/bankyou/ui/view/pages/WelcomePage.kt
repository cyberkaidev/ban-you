package com.cyberkaidev.bankyou.ui.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme

@Composable
fun WelcomePage(navController: NavHostController) {
    BankYouTheme {
        val configuration = LocalConfiguration.current

        Scaffold { innerPadding ->
            Surface(
                Modifier
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 25.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            "Welcome to \nBank you",
                            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Medium)
                        )
                        Text("Platform for monitoring your stablecoins")
                    }
                    TextButton(
                        onClick = { navController.navigate("RegisterPage") },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(configuration.screenWidthDp.div(1.90).dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                    ) {
                        Text(
                            "Continue",
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}