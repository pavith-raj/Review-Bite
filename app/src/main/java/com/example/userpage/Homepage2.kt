package com.example.userpage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Homepage2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage2)

        // Initialize ImageView variables
        val image1: ImageView = findViewById(R.id.image1)
        val image2: ImageView = findViewById(R.id.image2)
        val image3: ImageView = findViewById(R.id.image3)
        val image4: ImageView = findViewById(R.id.image4)
        val image5: ImageView = findViewById(R.id.image5)
        val image6: ImageView = findViewById(R.id.image6)
        val image7: ImageView = findViewById(R.id.image7)
        val image8: ImageView = findViewById(R.id.image8)
        val image9: ImageView = findViewById(R.id.image9)
        val image10: ImageView = findViewById(R.id.image10)

        // Set click listeners for each image
        image1.setOnClickListener {
            val intent = Intent(this, PalkiHotel::class.java)
            Toast.makeText(this, "Clicked on: $image1", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        image2.setOnClickListener {
            val intent = Intent(this, madhavans::class.java)
            Toast.makeText(this, "Clicked on: $image2", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        image3.setOnClickListener {
            val intent = Intent(this, NewAccent::class.java)
            Toast.makeText(this, "Clicked on: $image3", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        image4.setOnClickListener {
            val intent = Intent(this, Maharaja::class.java)
            Toast.makeText(this, "Clicked on: $image4", Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }

        image5.setOnClickListener {
            onImageClick("HOUSE OF COMMON")
        }

        image6.setOnClickListener {
            onImageClick("FROTH ON TOP")
        }



        // Set click listener for the review ImageView
        findViewById<ImageView>(R.id.image1).setOnClickListener {
            val intent = Intent(this@Homepage2, PalkiHotel::class.java)
            startActivity(intent)
        }
    }


    private fun onImageClick(restaurantName: String) {
        // Show a toast message

    }
}
