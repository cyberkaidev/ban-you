package com.cyberkaidev.bankyou.ui.view.shared

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ListViewItem(
    headlineContent: String,
    supportingContent: String? = null,
    leadingContent: @Composable() (() -> Unit)? = null,
    titleTrailing: String? = null,
    iconTrailing: ImageVector? = null,
    onLongPress: (() -> Unit)? = null,
    containerColor: Color? = MaterialTheme.colorScheme.surface,
) {
    ListItem(
        modifier = if(onLongPress != null) Modifier.clickable { onLongPress() } else Modifier,
        colors = ListItemColors(
            containerColor = containerColor ?: MaterialTheme.colorScheme.surface,
            headlineColor = MaterialTheme.colorScheme.onSurface,
            leadingIconColor = MaterialTheme.colorScheme.onSurface,
            overlineColor = MaterialTheme.colorScheme.onSurface,
            supportingTextColor = MaterialTheme.colorScheme.onSurface,
            trailingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledHeadlineColor =  Color.Transparent,
            disabledLeadingIconColor =  Color.Transparent,
            disabledTrailingIconColor =  Color.Transparent
        ),
        headlineContent = {
            Text(
                headlineContent,
                style = TextStyle(fontWeight = FontWeight.Medium)
            )
        },
        supportingContent = {
            if(!supportingContent.isNullOrEmpty()) Text(supportingContent)
        },
        leadingContent = leadingContent,
        trailingContent = {
            if(!titleTrailing.isNullOrEmpty()) {
                Text(
                    titleTrailing,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            if(iconTrailing !== null) {
                Icon(
                    imageVector = iconTrailing,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}