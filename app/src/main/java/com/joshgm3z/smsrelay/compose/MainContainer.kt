package com.joshgm3z.smsrelay.compose

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainContainer() {
    Column(Modifier.fillMaxSize()) {
        Text(text = "sms-relay")

        StatusBanner()

        SenderList()
    }
}

@Composable
fun SenderList() {
    LazyColumn {
        items(count = 10) {
            SenderItem()
        }
    }
}

@Composable
fun StatusBanner() {
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.LightGray)
                .padding(all = 3.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "info icon",
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "now listening to new sms. tap a checkbox " +
                        "to avoid forwarding to telegram",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainContainer() {
    MainContainer()
}