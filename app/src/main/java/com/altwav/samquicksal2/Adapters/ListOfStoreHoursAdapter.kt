package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.RSchedule
import kotlinx.android.synthetic.main.list_of_store_hours.view.*

class ListOfStoreHoursAdapter : RecyclerView.Adapter<ListOfStoreHoursAdapter.MyViewHolder>() {

    private var restaurantSchedule : List<RSchedule>? = null
    fun setRestaurantSchedule(restaurantSchedule1: List<RSchedule>?){
        this.restaurantSchedule = restaurantSchedule1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_store_hours, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurantSchedule?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (restaurantSchedule == null) {
            0
        } else {
            restaurantSchedule?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val scheduleDay: TextView = itemView.tvStoreDay
        private val scheduleTime: TextView = itemView.tvStoreTime

        fun bind(data: RSchedule){
            scheduleDay.text = data.Day
            scheduleTime.text = "${data.Opening} to ${data.Closing}"
        }
    }
}