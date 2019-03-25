package com.jeluchu.splashscreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import android.R.string.cancel
import android.annotation.SuppressLint


class SplashActivity : AppCompatActivity() {

    private var timer: Timer? = null
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progBar.progress = 0
        porcentaje.text = ""

        val period: Long = 100
        timer = Timer()
        timer!!.schedule(object:TimerTask() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                //this repeats every 100 ms
                if (i < 100) {
                    runOnUiThread { porcentaje.text = "$i%" }
                    progBar.progress = i
                    i++
                } else {
                    timer!!.cancel()
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 0, period)

    }
}
