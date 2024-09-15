package com.cyberkaidev.bankyou.ui.view.fragments

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DialogQRCodeKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            DialogQRCode(onDismissRequest = {}, onCopied = {}, address = "123")
        }

        composeTestRule.onNodeWithTag("dialogQRCode").isDisplayed()
    }
}