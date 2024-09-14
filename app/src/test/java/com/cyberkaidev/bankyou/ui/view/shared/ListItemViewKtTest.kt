package com.cyberkaidev.bankyou.ui.view.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.cyberkaidev.bankyou.data.di.appModule
import com.cyberkaidev.bankyou.data.di.networkModule
import com.cyberkaidev.bankyou.data.di.storageModule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ListItemViewKtTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(appModule, networkModule, storageModule)
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun listItemViewWithAction() {
        composeTestRule.setContent {
            ListItemView(
                headlineContent = "Hello",
                onLongPress = {}
            )
        }

        composeTestRule.onNodeWithTag("listItemViewClickable").isDisplayed()
        composeTestRule.onNodeWithTag("listItemViewClickable").performClick()
        composeTestRule.onNodeWithTag("listItemView").isNotDisplayed()
        composeTestRule.onNodeWithTag("headlineContent").isDisplayed()
    }

    @Test
    fun listItemViewWithoutAction() {
        composeTestRule.setContent {
            ListItemView(
                headlineContent = "Hello",
                onLongPress = {}
            )
        }

        composeTestRule.onNodeWithTag("listItemView").isDisplayed()
        composeTestRule.onNodeWithTag("listItemViewClickable").isNotDisplayed()
        composeTestRule.onNodeWithTag("headlineContent").isDisplayed()
    }

    @Test
    fun listItemViewRemainingProperties() {
        composeTestRule.setContent {
            ListItemView(
                headlineContent = "Cyber",
                supportingContent = "Kai",
                titleTrailing = "Dev",
                leadingContent = { Text("CyberKai") },
                iconTrailing = Icons.Default.Home,
                onLongPress = {}
            )
        }

        composeTestRule.onNodeWithTag("listItemView").isDisplayed()
        composeTestRule.onNodeWithTag("listItemViewClickable").isNotDisplayed()
        composeTestRule.onNodeWithTag("headlineContent").isDisplayed()
        composeTestRule.onNodeWithTag("supportingContent").isDisplayed()
        composeTestRule.onNodeWithTag("titleTrailing").isDisplayed()
        composeTestRule.onNodeWithTag("leadingContent").isDisplayed()
        composeTestRule.onNodeWithTag("iconTrailing").isDisplayed()
    }
}