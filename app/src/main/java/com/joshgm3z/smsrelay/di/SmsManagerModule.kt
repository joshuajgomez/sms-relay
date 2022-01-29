package com.joshgm3z.smsrelay.di

import android.content.Context
import com.joshgm3z.smsrelay.domain.SmsManager
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
    fun providesSmsManager(@ApplicationContext context: Context): SmsManager{
        return SmsManager(context)
    }

}