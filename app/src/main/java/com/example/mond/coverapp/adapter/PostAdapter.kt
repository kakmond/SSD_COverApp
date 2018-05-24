package com.example.mond.coverapp.adapter

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.mond.coverapp.ClickHandler
import com.example.mond.coverapp.R
import com.example.mond.coverapp.model.Post
import com.example.mond.coverapp.model.User

class PostAdapter(val posts: ArrayList<Post>, val user: User, var dealClickHandler: ClickHandler) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.name.text = posts.get(position).postBy.name
        holder!!.title.text = posts.get(position).title + " (" + posts.get(position).type + " " + posts.get(position).dealBy.size + "/" + posts.get(position).dealAmount + ")"
        holder!!.comment.text = posts.get(position).description
        holder!!.price.text = posts.get(position).price.toString() + " ฿"

        if (posts.get(position).postBy == user) {
            holder!!.dealButton.text = "POSTED"
            holder!!.dealButton.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
            holder!!.dealButton.isEnabled = false
        } else if (posts.get(position).isDealed(user)) {
            holder!!.dealButton.text = "DEALED"
            holder!!.dealButton.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
            holder!!.dealButton.isEnabled = false
        } else if (posts.get(position).isFull()) {
            holder!!.dealButton.text = "FULL"
            holder!!.dealButton.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
            holder!!.dealButton.isEnabled = false
        }
    }

    override fun getItemCount(): Int {
        return this.posts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.postlayout, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.txtName)
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val comment: TextView = itemView.findViewById(R.id.txtComment)
        val price: TextView = itemView.findViewById(R.id.txtPrice)
        val dealButton: Button = itemView.findViewById(R.id.dealButton)

        init {
            dealButton.setOnClickListener { view -> dealClickHandler!!.onClick(posts.get(adapterPosition)) }
        }

    }
}