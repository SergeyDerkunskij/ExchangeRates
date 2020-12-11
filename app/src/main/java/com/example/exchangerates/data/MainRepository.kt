package com.example.exchangerates.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.exchangerates.R
import com.example.exchangerates.data.model.Currency
import com.example.exchangerates.data.model.ExchangeRate
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainRepository {
    private var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val data = MutableLiveData<Currency>()

    fun getCurrencyFromAPI(date: LocalDate){
        var currency : Currency? = null
        val apiServices = RetrofitClient.pbApiServices
        apiServices.getCurrency(date.format(formatter).toString()).enqueue(object : Callback<Currency> {
            override fun onResponse(call: Call<Currency>,response: Response<Currency>) {
                currency = response.body()
                data.postValue(currency)
            }

            override fun onFailure(call: Call<Currency>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}