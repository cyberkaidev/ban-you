package com.cyberkaidev.bankyou.ui.view.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyberkaidev.bankyou.model.BalanceModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletCarousel(balance: BalanceModel) {
    val carouselState = rememberCarouselState { 2 }
    val cards = remember { listOf("USDC (Ethereum)", "USDT (Ethereum)") }
    val configuration = LocalConfiguration.current

    fun showBalance(value: String): String {
        if (value == "USDC (Ethereum)") return "$${balance.usdc}"
        return "$${balance.usdt}"
    }

    Box(Modifier.padding(vertical = 16.dp)) {
        HorizontalMultiBrowseCarousel(
            state = carouselState,
            modifier = Modifier.testTag("walletCarousel").width(412.dp).height(221.dp),
            preferredItemWidth = configuration.screenWidthDp.dp.div((1.3f)),
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(16.dp)
            ) {
                Text(
                    cards[it],
                    Modifier.align(Alignment.TopStart),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Column(Modifier.align(Alignment.BottomStart)) {
                    Text(
                        "Total balance",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        showBalance(cards[it]),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}