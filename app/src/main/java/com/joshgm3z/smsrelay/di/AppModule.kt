package com.joshgm3z.smsrelay.di

import androidx.room.Room
import com.joshgm3z.smsrelay.compose.MainViewModel
import com.joshgm3z.smsrelay.domain.SmsRepository
import com.joshgm3z.smsrelay.room.AppDatabase
import com.joshgm3z.smsrelay.ui.SenderViewModel
import com.joshgm3z.smsrelay.utils.SharedPref
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "sms_sender_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        SmsRepository(get())
    }

    viewModel {
        MainViewModel(get())
    }

    single {
        SharedPref(get())
    }

}