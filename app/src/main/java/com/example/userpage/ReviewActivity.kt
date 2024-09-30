package com.example.userpage // Change to your actual package name

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ReviewActivity : AppCompatActivity() {

    private lateinit var editTextReview: EditText
    private lateinit var buttonSubmitReview: Button
    private lateinit var recyclerViewReviews: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var database: DatabaseReference
    private val reviewsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // Initialize views
        editTextReview = findViewById(R.id.editTextReview)
        buttonSubmitReview = findViewById(R.id.buttonSubmitReview)
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews)

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().reference.child("Reviews")

        // Set up RecyclerView
        recyclerViewReviews.layoutManager = LinearLayoutManager(this)
        reviewAdapter = ReviewAdapter(reviewsList)
        recyclerViewReviews.adapter = reviewAdapter

        // Load existing reviews
        loadReviews()

        // Set click listener for submit button
        buttonSubmitReview.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        val reviewText = editTextReview.text.toString().trim()

        if (reviewText.isNotEmpty()) {
            // Create a unique key for each review
            val reviewId = database.push().key ?: return

            // Save review to Firebase
            database.child(reviewId).setValue(reviewText)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show()
                        editTextReview.text.clear() // Clear input after submission
                        loadReviews() // Reload reviews to update the list
                    } else {
                        Toast.makeText(this, "Failed to submit review.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please enter a review.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadReviews() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                reviewsList.clear() // Clear the list before adding new reviews
                for (snapshot in dataSnapshot.children) {
                    val review = snapshot.getValue(String::class.java)
                    review?.let { reviewsList.add(it) }
                }
                reviewAdapter.updateReviews(reviewsList) // Update the adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ReviewActivity, "Failed to load reviews.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
