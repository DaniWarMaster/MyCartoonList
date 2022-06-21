package com.example.android.mycartoonlist.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.dataclasses.Comment
import com.example.android.mycartoonlist.dataclasses.Data

class CommentsAdapter(arrayList: ArrayList<Comment>) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    var data = arrayList

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameText = itemView.findViewById<TextView>(R.id.commentElement_username)
        val dateText = itemView.findViewById<TextView>(R.id.commentELement_date)
        val commentText = itemView.findViewById<TextView>(R.id.commentElement_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_element, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.commentText.text = item.comment
        holder.usernameText.text = item.user
        holder.dateText.text = item.date
    }

    override fun getItemCount(): Int {
        return data.size
    }
}