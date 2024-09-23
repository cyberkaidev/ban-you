package com.cyberkaidev.bankyou.ui.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme
import com.cyberkaidev.bankyou.ui.view.shared.ButtonView
import com.cyberkaidev.bankyou.utils.resetRoute
import com.cyberkaidev.bankyou.viewmodel.BalanceViewModel
import com.cyberkaidev.bankyou.viewmodel.TransactionsViewModel
import com.cyberkaidev.bankyou.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(
    navController: NavHostController,
    userViewModel: UserViewModel,
    balanceViewModel: BalanceViewModel,
    transactionsViewModel: TransactionsViewModel,
) {
    BankYouTheme {
        val scope = rememberCoroutineScope()

        val clipboardManager = LocalClipboardManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        val isLoading = remember { mutableStateOf(false) }
        val addressField = remember { mutableStateOf("") }
        val snackbarHostState = remember { SnackbarHostState() }

        suspend fun onHandlerInformation() {
            if (addressField.value.isNotBlank()) {
                isLoading.value = true
                try {
                    balanceViewModel.getBalance(addressField.value)
                    transactionsViewModel.getTransactions(addressField.value)
                    userViewModel.setAddress(addressField.value)
                    resetRoute(navController, "HomePage/true")
                } catch (error: Throwable) {
                    isLoading.value = false
                    snackbarHostState.showSnackbar("An error occurred, try again")
                }
            }
        }

        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize(),
            topBar = {
                MediumTopAppBar(
                    title = { Text("Register") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { innerPadding ->
            Surface(
                Modifier
                    .padding(innerPadding)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            keyboardController!!.hide()
                        })
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(all = 16.dp)
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    OutlinedTextField(
                        value = addressField.value,
                        onValueChange = { addressField.value = it.trim() },
                        placeholder = { Text("Ethereum (Public Address)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            ButtonView(
                                onClick = {
                                    clipboardManager.getText()?.text?.let {
                                        addressField.value = it.trim()
                                    }
                                },
                                background = Color.Transparent) {
                                Text("Paste")
                            }
                        }
                    )

                    ButtonView(
                        enabled = !isLoading.value && addressField.value.isNotBlank(),
                        onClick = { scope.launch { onHandlerInformation() } }
                    ) {
                        Text(
                            if(isLoading.value) "Loading" else "Continue",
                            style = TextStyle(fontWeight = FontWeight.Medium),
                        )
                    }
                }
            }
        }
    }
}