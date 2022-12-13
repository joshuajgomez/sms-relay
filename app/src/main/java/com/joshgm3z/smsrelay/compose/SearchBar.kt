package com.joshgm3z.smsrelay.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joshgm3z.smsrelay.ui.theme.SmsRelayTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onSearchBackIconClick: () -> Unit,
    onSearchInputChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
    ) {
        val hSpace = 15.dp
        Spacer(modifier = Modifier.width(hSpace))
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = "close search",
            tint = Color.Gray,
            modifier = Modifier
                .size(ButtonDefaults.IconSize)
                .clickable { onSearchBackIconClick() }
        )
        Spacer(modifier = Modifier.width(hSpace))
        var searchInput by remember { mutableStateOf("") }
        TextField(
            label = { Text(text = "Search senders...") },
            value = searchInput,
            onValueChange = {
                searchInput = it
                onSearchInputChange(it)
            },
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
        )
        Spacer(modifier = Modifier.width(hSpace))
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = "clear input",
            tint = Color.Gray,
            modifier = Modifier
                .size(ButtonDefaults.IconSize)
                .clickable {
                    searchInput = ""
                    onSearchInputChange("")
                }
        )
        Spacer(modifier = Modifier.width(hSpace))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBar() {
    SmsRelayTheme {
        SearchBar({}) {}
    }
}