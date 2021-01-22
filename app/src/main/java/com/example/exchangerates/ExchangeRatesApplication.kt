package com.example.exchangerates

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class ExchangeRatesApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}