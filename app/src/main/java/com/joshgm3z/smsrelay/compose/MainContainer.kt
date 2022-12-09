package com.joshgm3z.smsrelay.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.getSampleList

@Composable
fun MainContainer(
    senderListLive: LiveData<List<Sender>>,
    onCheckedChangeClick: (sender: Sender) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        val senderList =
        if (isPreview()) getSampleList()
//            if (isPreview()) listOf()
            else senderListLive.observeAsState(initial = listOf()).value

        AnimatedVisibility(
            visible = senderList.isEmpty(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Sms,
                    contentDescription = "empty sms",
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "waiting for first sms",
                    color = Color.Gray,
                )
            }
        }

        AnimatedVisibility(visible = senderList.isNotEmpty()) {
            StatusBanner()
        }

        SenderList(senderList, onCheckedChangeClick)
    }
}


@Composable
fun SenderList(
    senderList: List<Sender>,
    onCheckedChangeClick: (sender: Sender) -> Unit
) {
    LazyColumn {
        itemsIndexed(items = senderList) { index, sender ->
            SenderItem(index + 1, sender, onCheckedChangeClick)
        }
    }
}

@Composable
fun isPreview(): Boolean = LocalInspectionMode.current

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainContainer() {
    MainContainer(MutableLiveData(), {})
}