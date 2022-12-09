package com.joshgm3z.smsrelay.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.joshgm3z.smsrelay.room.Sender

@Composable
fun SenderItem() {
    Card(shape = RoundedCornerShape(5.dp)) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            val (
                tvIndex,
                tvSenderName,
                tvMessageCount,
                tvTime,
                cbBlockStatus,
            ) = createRefs()

            Text(
                text = "1",
                modifier = Modifier
                    .constrainAs(tvIndex) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 20.dp)
                    }
                    .background(shape = CircleShape, color = Color.LightGray)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 2.dp,
                        bottom = 2.dp,
                    )
            )

            Text(
                text = "Sender",
                modifier = Modifier
                    .constrainAs(tvSenderName) {
                        top.linkTo(tvIndex.top)
                        bottom.linkTo(tvIndex.bottom)
                        start.linkTo(tvIndex.end, margin = 20.dp)
                    }
                    .offset(y = (-10).dp),
                color = Color.Black,
                fontSize = 20.sp
            )

            Text(
                text = "5 messages",
                modifier = Modifier
                    .constrainAs(tvMessageCount) {
                        top.linkTo(tvSenderName.bottom, margin = 0.dp)
                        start.linkTo(tvSenderName.start)
                    }
                    .offset(y = (-10).dp),
                color = Color.Gray,
                fontSize = 15.sp
            )

            Text(
                text = "5:34 pm",
                modifier = Modifier
                    .constrainAs(tvTime) {
                        top.linkTo(tvIndex.top)
                        bottom.linkTo(tvIndex.bottom)
                        end.linkTo(cbBlockStatus.start)
                    },
                fontSize = 13.sp,
                color = Color.Gray
            )

            Checkbox(
                checked = true,
                onCheckedChange = { },
                modifier = Modifier
                    .constrainAs(cbBlockStatus) {
                        top.linkTo(tvIndex.top)
                        bottom.linkTo(tvIndex.bottom)
                        end.linkTo(parent.end, margin = 10.dp)
                    }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSenderItem() {
    LazyColumn {
        items(count = 5) {
            SenderItem()
        }
    }
}