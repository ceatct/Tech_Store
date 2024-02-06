package com.inspire.techstore

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val r = Runnable {
            newIntent()
            finish()
        }
        val handler = HandlerCompat.createAsync(Looper.getMainLooper())
        handler.postDelayed(r, 3000)
    }

    private fun newIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}