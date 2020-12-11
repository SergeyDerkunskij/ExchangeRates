package com.example.exchangerates.data.model


import com.google.gson.annotations.SerializedName

data class Currency(
    val bank: String,
    val baseCurrency: Int,
    val baseCurrencyLit: String,
    val date: String,
    val exchangeRate: List<ExchangeRate>
)