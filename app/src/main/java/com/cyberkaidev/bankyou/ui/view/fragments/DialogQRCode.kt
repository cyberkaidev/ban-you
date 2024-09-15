package com.cyberkaidev.bankyou.ui.view.fragments

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cyberkaidev.bankyou.ui.view.shared.ButtonView
import com.cyberkaidev.bankyou.utils.generateQRCode

@Composable
fun DialogQRCode(
    onDismissRequest: () -> Unit,
    onCopied: () -> Unit,
    address: String
) {
    val clipboardManager = LocalClipboardManager.current
    val qrCodeColor = MaterialTheme.colorScheme.onSurface.toArgb()
    val qrCodeBackgroundColor = MaterialTheme.colorScheme.surface.toArgb()
    val qrCodeGenerated = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(address) {
        if (qrCodeGenerated.value === null) {
            qrCodeGenerated.value = generateQRCode(
                address,
                qrCodeColor,
                qrCodeBackgroundColor
            )
        }
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.testTag("dialogQRCode").fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 30.dp),
            ) {
                Text(
                    "Address",
                    modifier = Modifier.padding(bottom = 20.dp),
                    style = TextStyle(fontSize = 25.sp)
                )

                if (qrCodeGenerated.value != null) {
                    Image(
                        bitmap = qrCodeGenerated.value!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(220.dp).align(Alignment.CenterHorizontally)
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(25.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 15.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                Row(Modifier.align(Alignment.End).padding(top = 20.dp)) {
                    ButtonView(onClick = {
                        clipboardManager.setText(AnnotatedString(address))
                        onCopied()
                    }) {
                        Text("Copy address")
                    }
                }
            }
        }
    }
}