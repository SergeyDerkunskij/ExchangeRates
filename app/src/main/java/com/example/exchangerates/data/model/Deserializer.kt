package com.example.exchangerates.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import kotlin.collections.ArrayList

class Deserializer : JsonDeserializer<Currency>{
    override fun deserialize(json: JsonElement?,typeOfT: Type?,context: JsonDeserializationContext?): Currency? {
        val sDate = json?.asJsonObject?.get("date")?.asString
        val sBank = json?.asJsonObject?.get("bank")?.asString
        val sBaseCurrency = json?.asJsonObject?.get("baseCurrency")?.asInt
        val sBaseCurrencyLint = json?.asJsonObject?.get("baseCurrency")?.asString
        val arrResult = ArrayList<ExchangeRate>()
        val arr = json?.asJsonObject?.get("exchangeRate")?.asJsonArray
        arr?.let {
            for(i in 0..it.size()-1){
                val curr = it.get(i)
                val sBaseCurrency = curr?.asJsonObject?.get("baseCurrency")?.asString
                val sCurrency = curr?.asJsonObject?.get("currency")?.asString
                val purchaseRate = curr?.asJsonObject?.get("purchaseRate")?.asDouble
                val purchaseRateNB = curr?.asJsonObject?.get("purchaseRateNB")?.asDouble
                val saleRate = curr?.asJsonObject?.get("saleRate")?.asDouble
                val saleRateNB = curr?.asJsonObject?.get("saleRateNB")?.asDouble
                arrResult.add(ExchangeRate(sBaseCurrency,sCurrency,purchaseRate,purchaseRateNB,saleRate,saleRateNB))
            }
            val currResult = Currency(sBank?:"",sBaseCurrency?:980,sBaseCurrencyLint?:"",sDate?:"", arrResult)
            return currResult
        }?: kotlin.run {
            return null
        }
    }
}