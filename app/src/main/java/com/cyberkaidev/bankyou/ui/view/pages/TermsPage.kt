package com.cyberkaidev.bankyou.ui.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsPage(
    navController: NavHostController,
) {
    BankYouTheme {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        val showWebView = remember { mutableStateOf(true) }

        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize(),
            topBar = {
                MediumTopAppBar(
                    title = { Text("Terms") },
                    navigationIcon = {
                        IconButton(onClick = {
                            showWebView.value = false
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text("1 - Security", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp))
                    Text("1.1 - We do not store your public keys in databases;")
                    Text("1.2 - All information we collect is stored only on your device, we do not store it in databases.")
                    Spacer(Modifier.height(8.dp))
                    Text("2 - What we collect from you", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp))
                    Text("2.1 - Nothing.")
                    Spacer(Modifier.height(8.dp))
                    Text("3 - How we share your information", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp))
                    Text("3.1 - We only share your public key with the tatum.com library so that we can collect your balance information and transactions on the blockchain.")
                }
            }
        }
    }
}