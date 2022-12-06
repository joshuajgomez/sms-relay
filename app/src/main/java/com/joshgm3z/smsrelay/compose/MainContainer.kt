package com.joshgm3z.smsrelay.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainContainer() {
    Text(text = "Hello world!")
}

@Preview(showBackground = true)
@Composable
fun PreviewMainContainer() {
    MainContainer()
}