package com.joshgm3z.smsrelay.compose

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun MainContainer() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ActionBar()

        StatusBanner()

        SenderList()
    }
}

@Composable
fun ActionBar() {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .height(70.dp)
            .fillMaxWidth()
    ) {
        val (tvTitle, icSearch) = createRefs()
        Text(
            text = "sms-relay",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = MaterialTheme.colorScheme.surfaceTint,
            modifier = Modifier.constrainAs(tvTitle) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
        )
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "search",
            tint = Color.Gray,
            modifier = Modifier
                .size(25.dp)
                .constrainAs(icSearch) {
                    top.linkTo(tvTitle.top)
                    bottom.linkTo(tvTitle.bottom)
                    end.linkTo(parent.end)
                }
        )
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
                color = darkGray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainContainer() {
    MainContainer()
}