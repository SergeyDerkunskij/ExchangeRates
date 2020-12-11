package com.example.exchangerates.data

import com.example.exchangerates.data.model.Currency
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PBApiService {
    @GET("/p24api/exchange_rates?json")
    fun getCurrency(@Query("date") origin: String): Call<Currency>

}