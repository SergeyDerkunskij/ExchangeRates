package com.example.exchangerates.ui


import androidx.lifecycle.ViewModel
import com.example.exchangerates.data.MainRepository
import org.threeten.bp.Instant


class MainViewModel : ViewModel() {
    private val repository = MainRepository()
    val channel = repository.data

    fun getCurrencyFromAPI(instant: Instant){
        repository.getCurrencyFromAPI(instant)
    }

}