package com.joshgm3z.smsrelay.di

import android.content.Context
import androidx.room.Room
import com.joshgm3z.smsrelay.domain.SmsRepository
import com.joshgm3z.smsrelay.room.AppDatabase
import com.joshgm3z.smsrelay.ui.AdapterClickListener
import com.joshgm3z.smsrelay.ui.SenderAdapter
import com.joshgm3z.smsrelay.ui.SenderViewModel
import com.joshgm3z.smsrelay.utils.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SmsManagerModule {

    @Singleton
    @Provides
    fun providesSmsRepository(appDatabase: AppDatabase): SmsRepository {
        return SmsRepository(appDatabase)
    }

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sms_sender_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesSenderViewModel(model: SmsRepository): SenderViewModel {
        return SenderViewModel(model)
    }

    @Singleton
    @Provides
    fun providesSharedPref(@ApplicationContext context: Context): SharedPref {
        return SharedPref(context)
    }

    @Singleton
    @Provides
    fun providesSenderAdapter(
        sharedPrefs: SharedPref,
    ): SenderAdapter {
        return SenderAdapter(sharedPrefs)
    }

}