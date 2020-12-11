package com.example.exchangerates.data

import com.example.exchangerates.data.model.Currency
import com.example.exchangerates.data.model.Deserializer
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    val gson = GsonBuilder()
        .registerTypeAdapter(Currency::class.java, Deserializer())
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.privatbank.ua")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()


    val pbApiServices : PBApiService
        get() = retrofit.create(PBApiService::class.java)
}


