package com.example.userpage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class MainActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This handler will delay for 3 seconds and then launch the Registration page
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            finish() // Finish the splash screen activity so user can't go back to it
        }, SPLASH_TIME_OUT)
    }
}

