package com.example.exchangerates.ui

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.exchangerates.R
import com.example.exchangerates.data.model.ExchangeRate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_rates.*


class MyItemRecyclerViewAdapter(
    private val values: List<ExchangeRate>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_rates, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        if ((item.saleRateNB != null) and (item.saleRateNB!! <1)) {
            holder.bind(item,100)
        }
        else{
            holder.bind(item,1)
        }

        holder.currency_name.text = item.currency

       /*Я понимаю, что кусок далее смотрится очень топорно, но в API нет расшифровки валют.
       Пришлось сделать в лоб*/

        when (item.currency) {
            "EUR" -> {
                holder.currency_name.text = "Евро"
            }
            "USD" -> {
                holder.currency_name.text = "Доллар США"
            }
            "RUB" -> {
                holder.currency_name.text = "Российский рубль"
            }
            "CHF" -> {
                holder.currency_name.text = "Швейцарский франк"
            }
            "GBP" -> {
                holder.currency_name.text = "Британский фунт"
            }
            "PLZ" -> {
                holder.currency_name.text = "Польский злотый"
            }
            "SEK" -> {
                holder.currency_name.text = "Шведская крона"
            }
            "XAU" -> {
                holder.currency_name.text = "Золото"
            }
            "CAD" -> {
                holder.currency_name.text = "Канадский доллар"
            }
            "UAH" -> {
                holder.currency_name.text = "Гривна"
            }
            "TRY" -> {
                holder.currency_name.text = "Турецкая лира"
            }
            "AZN" -> {
                holder.currency_name.text = "Азербайджанский манат"
            }
            "BYN" -> {
                holder.currency_name.text = "Белорусский рубль"
            }
            "CNY" -> {
                holder.currency_name.text = "Китайский юань"
            }
            "CZK" -> {
                holder.currency_name.text = "Чешская крона"
            }
            "DKK" -> {
                holder.currency_name.text = "Датская крона"
            }
            "GEL" -> {
                holder.currency_name.text = "Грузинский лари"
            }
            "HUF" -> {
                holder.currency_name.text = "Венгерский форинт"
            }
            "ILS" -> {
                holder.currency_name.text = "Новый израильский шекель"
            }
            "JPY" -> {
                holder.currency_name.text = "Японская иена"
            }
            "KZT" -> {
                holder.currency_name.text = "Казахстанский тенге"
            }
            "MDL" -> {
                holder.currency_name.text = "Молдавский лей"
            }
            "NOK" -> {
                holder.currency_name.text = "Норвежская крона"
            }
            "SGD" -> {
                holder.currency_name.text = "Сингапурский доллар"
            }
            "TMT" -> {
                holder.currency_name.text = "Туркменский манат"
            }
            "UZS" -> {
                holder.currency_name.text = "Узбекский сум"
            }
        }

        if (position%2 == 1) {
            holder.currency_rate_layout.setBackgroundColor(Color.parseColor("#EFF4F0"))
        }
        else{
            holder.currency_rate_layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: ExchangeRate, moneyNumber: Int){
            val rounded = String.format("%.2f", item.saleRateNB?.times(moneyNumber))
            sale_rate_nb.text = rounded + " " + item.baseCurrency
            currency.text = "$moneyNumber " + item.currency
        }
    }

}