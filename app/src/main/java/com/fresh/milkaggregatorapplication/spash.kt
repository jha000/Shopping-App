package com.fresh.milkaggregatorapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fresh.milkaggregatorapplication.R
import android.content.Intent
import android.os.Handler

class spash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash)
        Handler().postDelayed({
            val i = Intent(
                this@spash,
                MainActivity::class.java
            )
            startActivity(i)
            finish()
        }, SPLASH_SCREEN_TIME_OUT.toLong())
    }

    companion object {
        private const val SPLASH_SCREEN_TIME_OUT = 2000
    }
}