package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R

class RatedRestaurantsAdapter : RecyclerView.Adapter<RatedRestaurantsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rated_restaurants, parent, false)
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