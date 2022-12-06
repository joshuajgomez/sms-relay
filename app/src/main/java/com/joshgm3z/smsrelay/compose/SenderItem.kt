package com.joshgm3z.smsrelay.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SenderItem() {
    ConstraintLayout {
        val (tvIndex, tvSenderName, tvMessageCount, tvTime, cbBlockStatus) = createRefs()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSenderItem() {
    SenderItem()
}