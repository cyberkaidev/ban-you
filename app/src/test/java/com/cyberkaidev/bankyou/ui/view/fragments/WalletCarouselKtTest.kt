package com.cyberkaidev.bankyou.ui.view.fragments

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.cyberkaidev.bankyou.model.BalanceModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WalletCarouselKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            WalletCarousel(BalanceModel(usdc = "123", usdt = "456"))
        }

        composeTestRule.onNodeWithTag("walletCarousel").isDisplayed()
    }
}