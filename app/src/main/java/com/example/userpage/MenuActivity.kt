// MenuActivity2.kt
package com.example.userpage

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() { // Ensure the class name matches your intended activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu) // Make sure this matches your layout file name

        // Initialize the back button
        val buttonBack = findViewById<Button>(R.id.btnbackk) // Ensure this ID matches your XML

        // Set an onClickListener for the button to go back to the previous activity
        buttonBack.setOnClickListener {
            // End the current activity and go back to the previous one
            finish()
        }
    }
}
