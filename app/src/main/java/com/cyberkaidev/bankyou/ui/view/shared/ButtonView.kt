package com.cyberkaidev.bankyou.ui.view.shared

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

@Composable
fun ButtonView(
    onClick: () -> Unit,
    background: Color? = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
    enabled: Boolean? = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = Modifier.testTag("buttonView"),
        onClick = onClick,
        enabled = enabled ?: true,
        colors = ButtonColors(
            containerColor = background ?: MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
            disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            contentColor = MaterialTheme.colorScheme.primary
        ),
        content = content,
    )
}