package com.cyberkaidev.bankyou.ui.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cyberkaidev.bankyou.R
import com.cyberkaidev.bankyou.model.NetworkStatusModel
import com.cyberkaidev.bankyou.model.TransactionSubtypeModel
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme
import com.cyberkaidev.bankyou.ui.view.fragments.ErrorPage
import com.cyberkaidev.bankyou.ui.view.shared.ListItemView
import com.cyberkaidev.bankyou.ui.view.fragments.WalletCarousel
import com.cyberkaidev.bankyou.viewmodel.BalanceViewModel
import com.cyberkaidev.bankyou.viewmodel.TransactionsViewModel
import com.cyberkaidev.bankyou.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    wasItLoaded: Boolean? = false,
    navController: NavHostController,
    userViewModel: UserViewModel,
    balanceViewModel: BalanceViewModel,
    transactionsViewModel: TransactionsViewModel
) {
    BankYouTheme {
        val status = rememberSaveable { mutableStateOf(NetworkStatusModel.UNINITIALIZED) }

        val address by userViewModel.address.collectAsState(initial = "")
        val transactions by transactionsViewModel.transactions.collectAsState()
        val balance by balanceViewModel.balance.collectAsState()

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        val scope = rememberCoroutineScope()

        fun addStatusAmount(type: TransactionSubtypeModel, amount: String): String {
            if (type == TransactionSubtypeModel.incoming) return "+ $amount"
            return "- $amount"
        }

        fun getBalanceAndTransactions() {
            scope.launch {
                try {
                    status.value = NetworkStatusModel.LOADING
                    balanceViewModel.getBalance(address)
                    transactionsViewModel.getTransactions(address)
                    status.value = NetworkStatusModel.SUCCESS
                } catch (error: Throwable) {
                    status.value = NetworkStatusModel.FAILED
                }
            }
        }

        LaunchedEffect(wasItLoaded, address) {
            if (wasItLoaded == true && status.value === NetworkStatusModel.UNINITIALIZED) {
                status.value = NetworkStatusModel.SUCCESS
                return@LaunchedEffect
            }

            if (address.isNotBlank() && status.value === NetworkStatusModel.UNINITIALIZED) {
                getBalanceAndTransactions()
            }
        }

        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize(),
            topBar = {
                MediumTopAppBar(
                    title = { Text("Bank you") },
                    actions = {
                        IconButton(onClick = { navController.navigate("SettingsPage") }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
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
                    if (status.value === NetworkStatusModel.SUCCESS) {
                        WalletCarousel(balance)

                        transactions.transactions.forEach { category ->
                            Text(
                                category.date,
                                style = TextStyle(fontSize = 14.sp),
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                            )
                            category.items.forEach {
                                ListItemView(
                                    headlineContent = it.tokenName.name,
                                    supportingContent = it.hours,
                                    titleTrailing = addStatusAmount(it.transactionSubtype, it.amount),
                                    leadingContent = {
                                        Box(
                                            Modifier
                                                .width(40.dp)
                                                .height(40.dp)
                                                .clip(shape = RoundedCornerShape(40.dp))
                                                .background(MaterialTheme.colorScheme.primaryContainer)
                                        ) {
                                            fun iconTransactionID(): Int {
                                                if (it.transactionSubtype == TransactionSubtypeModel.incoming) {
                                                    return R.drawable.icon_deposit
                                                }
                                                return R.drawable.icon_withdraw
                                            }

                                            Icon(
                                                painter = painterResource(iconTransactionID()),
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.align(Alignment.Center)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                if (status.value === NetworkStatusModel.FAILED) {
                    ErrorPage(onClick = { getBalanceAndTransactions() })
                }

                if (status.value === NetworkStatusModel.LOADING) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}