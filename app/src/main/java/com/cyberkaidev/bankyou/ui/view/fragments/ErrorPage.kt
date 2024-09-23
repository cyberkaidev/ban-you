package com.cyberkaidev.bankyou.ui.view.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cyberkaidev.bankyou.ui.view.shared.ButtonView

@Composable
fun ErrorPage(onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Ops, an internal error occurred",
            modifier = Modifier.padding(bottom = 10.dp).testTag("title"),
            style = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        )
        ButtonView(onClick = onClick) {
            Text("Try again", Modifier.testTag("text-button"))
        }
    }
}