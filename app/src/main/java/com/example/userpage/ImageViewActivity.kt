// ImageViewActivity.kt
package com.example.userpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class ImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        val imageView = findViewById<ImageView>(R.id.full_image_view)

        // Get image resource ID from Intent
        val imageResId = intent.getIntExtra("imageResId", 0)

        // Set the ImageView resource
        imageView.setImageResource(imageResId)
    }
}
