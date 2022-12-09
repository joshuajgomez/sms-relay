package com.joshgm3z.smsrelay.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusBanner() {
    val horizontalPadding = 20.dp
    Card(
        modifier = Modifier
            .padding(
                start = horizontalPadding,
                end = horizontalPadding
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(veryLightGray)
                .padding(all = 10.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "info icon",
                modifier = Modifier.size(23.dp),
                tint = MaterialTheme.colorScheme.surfaceTint
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "now listening to new sms. tap a checkbox " +
                        "to avoid forwarding to telegram",
                fontSize = 15.sp,
                color = darkGray,
                lineHeight = 15.sp
            )
        }
    }
}