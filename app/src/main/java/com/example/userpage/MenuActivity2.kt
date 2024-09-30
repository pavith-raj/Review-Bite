// MenuActivity2.kt
package com.example.userpage

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2)

        // Initialize the back button
        val buttonBack = findViewById<Button>(R.id.btnback) // Use the correct ID

        // Set an onClickListener for the button to go back to the previous activity
        buttonBack.setOnClickListener {
            // End the current activity and go back to the previous one
            finish()
        }
    }
}
