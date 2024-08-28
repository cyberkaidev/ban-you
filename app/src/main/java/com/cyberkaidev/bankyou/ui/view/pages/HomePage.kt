package com.cyberkaidev.bankyou.ui.view.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.cyberkaidev.bankyou.model.TransactionSubtypeModel
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme
import com.cyberkaidev.bankyou.ui.view.shared.ListViewItem
import com.cyberkaidev.bankyou.ui.view.fragments.WalletCarousel
import com.cyberkaidev.bankyou.viewmodel.BalanceViewModel
import com.cyberkaidev.bankyou.viewmodel.TransactionsViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavHostController,
    balanceViewModel: BalanceViewModel,
    transactionsViewModel: TransactionsViewModel
) {
    BankYouTheme {
        val transactions by transactionsViewModel.transactions.collectAsState()
        val balance by balanceViewModel.balance.collectAsState()

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

        fun addStatusAmount(type: TransactionSubtypeModel, amount: String): String {
            if (type == TransactionSubtypeModel.incoming) return "+ $amount"
            return "- $amount"
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
                    WalletCarousel(balance)

                    transactions.transactions.forEach { category ->
                        Text(
                            category.date,
                            style = TextStyle(fontSize = 14.sp),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        category.items.forEach {
                            ListViewItem(
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
        }
    }
}