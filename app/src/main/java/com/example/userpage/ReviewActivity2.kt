// ReviewActivity.kt
package com.example.userpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReviewActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review2)

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.review_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Example review data
        val reviews = listOf(
            "Great food, loved the experience!",
            "Service was a bit slow, but the food was amazing.",
            "Not bad, but I expected more from the ambiance.",
            "Excellent staff, and the dessert was fantastic!"
        )

        // Set the adapter
        recyclerView.adapter = ReviewAdapter(reviews)
    }
}
