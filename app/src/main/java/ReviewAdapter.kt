package com.example.userpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private var reviews: List<String>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() { // Changed UserReviewAdapter to ReviewAdapter

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textViewReview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.textView.text = reviews[position]
    }

    override fun getItemCount() = reviews.size

    fun updateReviews(newReviews: List<String>) {
        reviews = newReviews
        notifyDataSetChanged()
    }
}
