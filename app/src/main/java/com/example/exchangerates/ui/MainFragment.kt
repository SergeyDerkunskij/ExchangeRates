package com.example.exchangerates.ui

import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exchangerates.R
import com.example.exchangerates.data.model.Currency
import kotlinx.android.synthetic.main.fragment_main.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val day = calendar.get(Calendar.DAY_OF_MONTH)

    private lateinit var currency : Currency
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDate(LocalDate.now())

        pik_date_button_pb.setOnClickListener(pickDateClickListener)
        pik_date_button_nb.setOnClickListener(pickDateClickListener)

        rub_layout.setOnClickListener(RUBClickListener)
        usd_layout.setOnClickListener(USDClickListener)
        eur_layout.setOnClickListener(EURClickListener)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.channel.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            currency = it
            fillInData(currency)
        })
        viewModel.getCurrencyFromAPI(LocalDate.now())
    }



    private fun fillInData(currency:Currency){
        for (exchangeRate in currency.exchangeRate) {
            when (exchangeRate.currency) {
                "EUR" -> {
                    eur_buy.text = String.format("%.3f", exchangeRate.purchaseRate)
                    eur_sold.text = String.format("%.3f", exchangeRate.saleRate)
                }
                "USD" -> {
                    usd_buy.text = String.format("%.3f", exchangeRate.purchaseRate)
                    usd_sold.text = String.format("%.3f", exchangeRate.saleRate)
                }
                "RUB" -> {
                    rur_buy.text = String.format("%.3f", exchangeRate.purchaseRate)
                    rur_sold.text = String.format("%.3f", exchangeRate.saleRate)
                }
            }
        }

        with(exchange_rate_list) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MyItemRecyclerViewAdapter(currency.exchangeRate)
        }
    }

    private fun setDate(date:LocalDate) {
        pb_date_text.paintFlags = pb_date_text.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        nb_date_text.paintFlags = nb_date_text.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        pb_date_text.text = date.format(formatter).toString()
        nb_date_text.text = date.format(formatter).toString()
    }

    private val pickDateClickListener: View.OnClickListener = View.OnClickListener {
        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, mYear, mMonthOfYear, mDayOfMonth ->
            // Display Selected date in TextView
            setDate(LocalDate.of(mYear,mMonthOfYear+1,mDayOfMonth))

            viewModel.getCurrencyFromAPI(LocalDate.of(mYear,mMonthOfYear+1,mDayOfMonth))
        }, year, month, day)
        dpd.show()
    }



    private val RUBClickListener: View.OnClickListener = View.OnClickListener { view ->
        moveToPosition("RUB")
    }
    private val USDClickListener: View.OnClickListener = View.OnClickListener { view ->
        moveToPosition("USD")
    }
    private val EURClickListener: View.OnClickListener = View.OnClickListener { view ->
        moveToPosition("EUR")
    }

    private fun moveToPosition(currencyName : String) {
        var position = 0

        for (i in currency.exchangeRate.indices) {
            when (currency.exchangeRate[i].currency) {
                currencyName -> {
                    position = i
                }
            }
        }
        exchange_rate_list.post { exchange_rate_list.smoothScrollToPosition(position) }
    }

}