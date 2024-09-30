// PalkiHotel.kt
package com.example.userpage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView

class PalkiHotel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palki_hotel)

        // Image click event for Image1
        findViewById<ImageView>(R.id.image1).setOnClickListener {
            openFullImage(R.drawable.p1)
        }

        // Image click event for Image2
        findViewById<ImageView>(R.id.image2).setOnClickListener {
            openFullImage(R.drawable.p2)
        }

        // Other image click events
        findViewById<ImageView>(R.id.image3).setOnClickListener {
            openFullImage(R.drawable.pf1)
        }
        findViewById<ImageView>(R.id.image4).setOnClickListener {
            openFullImage(R.drawable.pf2)
        }

        // Menu button click event
        findViewById<Button>(R.id.button_menu).setOnClickListener {
            val intent = Intent(this@PalkiHotel, MenuActivity::class.java)
            startActivity(intent)
        }

        // Reviews button click event
        findViewById<Button>(R.id.button_review).setOnClickListener {
            val intent = Intent(this@PalkiHotel, ReviewActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to open the image in full screen
    private fun openFullImage(imageResId: Int) {
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("imageResId", imageResId)
        startActivity(intent)
    }
}
