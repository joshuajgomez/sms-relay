package com.joshgm3z.smsrelay.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TelegramBotService {
    @GET("sendMessage")
    fun sendMessage(
        @Query("chat_id") chat_id: String?,
        @Query("text") text: String?
    ): Call<ResponseBody?>?
}