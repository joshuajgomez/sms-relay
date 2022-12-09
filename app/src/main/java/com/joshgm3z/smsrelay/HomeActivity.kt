@file:OptIn(ExperimentalPermissionsApi::class)

package com.joshgm3z.smsrelay

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.joshgm3z.smsrelay.compose.*
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.ui.theme.SmsRelayTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsRelayTheme {
                val mainViewModel: MainViewModel by viewModel()
                val permissionState = rememberPermissionState(
                    permission = Manifest.permission.RECEIVE_SMS
                )
                HomeContainer(
                    isSmsPermissionGranted = permissionState.hasPermission,
                    senderListLive = mainViewModel.senderList,
                    onSearchIconClick = { mainViewModel.onSearchIconClick() },
                    onCheckedChange = { mainViewModel.onCheckedChange(it) },
                    onAskForPermission = { permissionState.launchPermissionRequest() }
                )
            }
        }
    }

}

@Composable
fun HomeContainer(
    isSmsPermissionGranted: Boolean,
    senderListLive: LiveData<List<Sender>>,
    onSearchIconClick: () -> Unit,
    onCheckedChange: (sender: Sender) -> Unit,
    onAskForPermission: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        ActionBar { onSearchIconClick() }

        if (isSmsPermissionGranted)
            MainContainer(
                senderListLive = senderListLive,
                onCheckedChangeClick = { onCheckedChange(it) }
            )
        else
            PermissionError { onAskForPermission() }

    }
}

@Composable
fun PermissionError(askForPermission: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = "error icon",
            modifier = Modifier.size(80.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "please give sms permission to let the " +
                    "app listen to incoming sms",
            modifier = Modifier.width(280.dp),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { askForPermission() }
        ) {
            Text(text = "Give Permission")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SmsRelayTheme {
        HomeContainer(
            isSmsPermissionGranted = true,
            senderListLive = MutableLiveData(),
            onCheckedChange = {},
            onSearchIconClick = {},
            onAskForPermission = {},
        )
    }
}