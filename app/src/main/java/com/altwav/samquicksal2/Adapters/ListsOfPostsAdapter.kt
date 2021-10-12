package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R

class ListsOfPostsAdapter : RecyclerView.Adapter<ListsOfPostsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lists_of_posts, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 5
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}