/*package com.example.userpage // Change to your actual package name

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserReviewAdapter(private var reviews: List<String>) : RecyclerView.Adapter<UserReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reviewTextView: TextView = view.findViewById(android.R.id.text1) // Use a simple TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.reviewTextView.text = reviews[position]
    }

    override fun getItemCount() = reviews.size

    fun updateReviews(newReviews: List<String>) {
        reviews = newReviews
        notifyDataSetChanged()
    }
}
*/