package com.cyberkaidev.bankyou.ui.view.fragments

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ErrorPageKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            ErrorPage(onClick = {})
        }

        composeTestRule.onNodeWithTag("title").isDisplayed()
        composeTestRule.onNodeWithTag("text-button").isDisplayed()
        composeTestRule.onNodeWithTag("buttonView").performClick()
    }
}