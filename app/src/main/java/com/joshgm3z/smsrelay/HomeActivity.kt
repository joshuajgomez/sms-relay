@file:OptIn(ExperimentalPermissionsApi::class)

package com.joshgm3z.smsrelay

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                val isSearchMode = mainViewModel.isSearchMode.value
                HomeContainer(
                    isSmsPermissionGranted = permissionState.hasPermission,
                    senderListLive =
                    if (isSearchMode) mainViewModel.senderListResult
                    else mainViewModel.senderList,
                    onSearchIconClick = { mainViewModel.onSearchIconClick() },
                    onCheckedChange = { mainViewModel.onCheckedChange(it) },
                    onAskForPermission = { permissionState.launchPermissionRequest() },
                    isSearchMode = isSearchMode,
                    onAppTitleLongClick = { mainViewModel.onAppTitleLongClick() },
                    onSearchBackIconClick = { mainViewModel.onSearchBackIconClick() },
                    onSearchInputChange = { mainViewModel.onSearchInputChanged(it) }
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
    onAppTitleLongClick: () -> Unit,
    onCheckedChange: (sender: Sender) -> Unit,
    onAskForPermission: () -> Unit,
    isSearchMode: Boolean,
    onSearchBackIconClick: () -> Unit,
    onSearchInputChange: (String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        if (isSearchMode)
            SearchBar(
                onSearchInputChange = { onSearchInputChange(it) },
                onSearchBackIconClick = { onSearchBackIconClick() }
            )
        else
            ActionBar(
                onAppTitleLongClick = { onAppTitleLongClick() },
                onSearchIconClick = { onSearchIconClick() }
            )

        if (isSmsPermissionGranted)
            ListContainer(
                senderListLive = senderListLive,
                onCheckedChangeClick = { onCheckedChange(it) },
                isSearchMode = isSearchMode
            )
        else
            PermissionError { onAskForPermission() }

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
            isSearchMode = false,
            onAppTitleLongClick = {},
            onSearchBackIconClick = {},
            onSearchInputChange = {},
        )
    }
}