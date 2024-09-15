package com.cyberkaidev.bankyou.ui.view.shared

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.cyberkaidev.bankyou.ui.theme.BankYouTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ButtonViewKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            BankYouTheme {
                ButtonView(onClick = {}) {
                    Text("Hello", modifier = Modifier.testTag("text"))
                }
            }
        }

        composeTestRule.onNodeWithTag("text").isDisplayed()
        composeTestRule.onNodeWithTag("buttonView").performClick()
    }
}