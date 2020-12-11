package com.example.exchangerates.data.model


import com.google.gson.annotations.SerializedName

data class ExchangeRate(
    val baseCurrency: String?,
    val currency: String?,
    val purchaseRate: Double?,
    val purchaseRateNB: Double?,
    val saleRate: Double?,
    val saleRateNB: Double?
)