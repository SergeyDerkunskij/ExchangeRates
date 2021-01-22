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
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class MainFragment : Fragment(R.layout.fragment_main) {

    private var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(ZoneId.systemDefault())

    private lateinit var currency : Currency
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDate(Instant.now())

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
        viewModel.getCurrencyFromAPI(Instant.now())
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

    private fun setDate(instant: Instant) {
        pb_date_text.paintFlags = pb_date_text.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        nb_date_text.paintFlags = nb_date_text.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        pb_date_text.text = formatter.format(instant).toString()
        nb_date_text.text = formatter.format(instant).toString()
    }

    private val pickDateClickListener: View.OnClickListener = View.OnClickListener {
        val dateForRequest = LocalDateTime.now()

        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, mYear, mMonthOfYear, mDayOfMonth ->
            // Display Selected date in TextView
            val zonedDateTime = LocalDateTime.of(mYear, mMonthOfYear+1, mDayOfMonth,1,1)
                .atZone(ZoneId.systemDefault()).toInstant()

            setDate(zonedDateTime)

            viewModel.getCurrencyFromAPI(zonedDateTime)
        }, dateForRequest.year,
            dateForRequest.month.value-1,
            dateForRequest.dayOfMonth)
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