package com.joshgm3z.smsrelay.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.joshgm3z.smsrelay.ui.theme.SmsRelayTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActionBar(
    onSearchIconClick: () -> Unit,
    onAppTitleLongClick: () -> Unit,
) {
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
            fontFamily = FontFamily.Default,
            color = MaterialTheme.colorScheme.surfaceTint,
            modifier = Modifier
                .constrainAs(tvTitle) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .combinedClickable(
                    onLongClick = { onAppTitleLongClick() },
                    onClick = {}
                )
        )
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "search",
            tint = Color.Gray,
            modifier = Modifier
                .size(ButtonDefaults.IconSize)
                .constrainAs(icSearch) {
                    top.linkTo(tvTitle.top)
                    bottom.linkTo(tvTitle.bottom)
                    end.linkTo(parent.end)
                }
                .clickable { onSearchIconClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewActionBar() {
    SmsRelayTheme {
        ActionBar({}, {})
    }
}