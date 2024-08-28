package com.cyberkaidev.bankyou.ui.view.fragments

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DialogAddressesInput(
    onDismissRequest: () -> Unit,
    address: String
) {
    val ethereumField = remember { mutableStateOf(address) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 30.dp),
            ) {
                Text(
                    "Your public key",
                    modifier = Modifier.padding(bottom = 20.dp),
                    style = TextStyle(fontSize = 25.sp)
                )

                OutlinedTextField(
                    value = ethereumField.value,
                    onValueChange = { ethereumField.value = it.trim() },
                    placeholder = { Text("Ethereum") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }
    }
}