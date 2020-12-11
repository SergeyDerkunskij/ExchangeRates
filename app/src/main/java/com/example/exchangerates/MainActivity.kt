package com.example.exchangerates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exchangerates.ui.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, MainFragment())
                    .commit()
        }
    }
}