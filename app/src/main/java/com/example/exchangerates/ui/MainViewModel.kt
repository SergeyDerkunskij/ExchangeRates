package com.example.exchangerates.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.exchangerates.data.MainRepository
import com.example.exchangerates.data.RetrofitClient
import com.example.exchangerates.data.model.Currency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel : ViewModel() {
    private val repository = MainRepository()
    val channel = repository.data

    fun getCurrencyFromAPI(date: LocalDate){
        repository.getCurrencyFromAPI(date)
    }

}