package com.cyberkaidev.bankyou.ui.view.pages

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme
import com.cyberkaidev.bankyou.ui.view.fragments.DialogAddressesInput
import com.cyberkaidev.bankyou.ui.view.shared.ButtonView
import com.cyberkaidev.bankyou.ui.view.shared.ListViewItem
import com.cyberkaidev.bankyou.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    BankYouTheme {
        val address by userViewModel.address.collectAsState(initial = "")

        val scope = rememberCoroutineScope()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        val settings = remember { listOf("Address", "Terms", "Exit") }
        val openAddressDialog = remember { mutableStateOf(false) }
        val openExitDialog = remember { mutableStateOf(false) }

        fun setAddress(value: String) {
            scope.launch {
                userViewModel.setAddress(value)
            }
        }

        fun onHandlerExit() {
            openExitDialog.value = false
            setAddress("")
            navController.navigate("WelcomePage") {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }

        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize(),
            topBar = {
                MediumTopAppBar(
                    title = { Text("Settings") },
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
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    settings.forEach {
                        ListViewItem(
                            headlineContent = it,
                            onLongPress = {
                                when(it) {
                                    "Address" -> { openAddressDialog.value = true }
                                    "Terms" -> { navController.navigate("TermsPage") }
                                    "Exit" -> { openExitDialog.value = true }
                                }
                            },
                            iconTrailing = Icons.AutoMirrored.Filled.KeyboardArrowRight
                        )
                    }
                }

                if (openAddressDialog.value) {
                    DialogAddressesInput(
                        onDismissRequest = { openAddressDialog.value = false },
                        address = address
                    )
                }

                if (openExitDialog.value) {
                    AlertDialog(
                        title = {  Text("Do you really want to leave?") },
                        onDismissRequest = { openExitDialog.value = false },
                        confirmButton = {
                            ButtonView(onClick = { onHandlerExit() }) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            ButtonView(
                                onClick = { openExitDialog.value = false },
                                background = Color.Transparent
                            ) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}