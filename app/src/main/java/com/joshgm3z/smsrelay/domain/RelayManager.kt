package com.joshgm3z.smsrelay.domain

import com.joshgm3z.smsrelay.retrofit.TelegramBotService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RelayManager {

    companion object {
        // https://api.telegram.org/bot584181575:AAHOjDEMEAx1dyzUh5WO2HV_wc-R7kcUVJI/sendMessage";
        private const val BASE_URL = "https://api.telegram.org/bot"
        private const val API_TOKEN = "2131740122:AAFHxy5Dp5iM2jcTYu1ReBOM5WfHyVSKdLk/"
        private const val CHAT_ID = "557247651"
        private var telegramBot: TelegramBotService? = null
    }

    init {
        if (telegramBot == null) {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL + API_TOKEN)
                .build()
            telegramBot = retrofit.create(TelegramBotService::class.java)
        } else {
            // Do not re-create if instance is already available
        }
    }

    fun relaySms(sender: String, message: String) {
        telegramBot
            ?.sendMessage(CHAT_ID, "From $sender:\n$message")
            ?.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>?,
                    response: Response<ResponseBody?>?
                ) {
                }

                override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {}
            })
    }
}