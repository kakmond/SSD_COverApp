package com.example.mond.coverapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mond.coverapp.ClickHandler
import com.example.mond.coverapp.R
import com.example.mond.coverapp.model.Post

class DealAdapter(val deals: ArrayList<Post>, var clicked: ClickHandler) : RecyclerView.Adapter<DealAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.title.text = deals.get(position).title
        holder!!.des.text = deals.get(position).description
    }

    override fun getItemCount(): Int {
        return this.deals.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.deallayout, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val des: TextView = itemView.findViewById(R.id.des)
        var avatar: ImageView = itemView.findViewById(R.id.avatar)

        init {
            avatar.setOnClickListener { view -> clicked!!.onClick(deals.get(adapterPosition)) }
        }
    }

}