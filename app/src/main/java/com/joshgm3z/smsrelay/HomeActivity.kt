package com.joshgm3z.smsrelay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import com.joshgm3z.smsrelay.compose.MainContainer
import com.joshgm3z.smsrelay.compose.MainViewModel
import com.joshgm3z.smsrelay.ui.theme.SmsRelayTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsRelayTheme {
                val mainViewModel: MainViewModel by viewModel()
                MainContainer(
                    senderListLive = mainViewModel.senderList,
                    onSearchIconClick = { mainViewModel.onSearchIconClick() },
                    onCheckedChangeClick = { mainViewModel.onCheckedChange(it) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SmsRelayTheme {
        MainContainer(
            senderListLive = MutableLiveData(),
            onSearchIconClick = { },
            onCheckedChangeClick = {}
        )
    }
}