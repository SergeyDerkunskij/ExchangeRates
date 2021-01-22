package com.example.exchangerates.data

import androidx.lifecycle.MutableLiveData
import com.example.exchangerates.data.model.Currency
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class MainRepository {
    private var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(ZoneId.systemDefault())
    val data = MutableLiveData<Currency>()

    fun getCurrencyFromAPI(instant: Instant){
        var currency : Currency? = null
        val apiServices = RetrofitClient.pbApiServices
        apiServices.getCurrency(formatter.format(instant)).enqueue(object : Callback<Currency> {
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


