package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.NotifBOffenseHist
import kotlinx.android.synthetic.main.list_of_offense.view.*

class ListOfOffenseAdapter: RecyclerView.Adapter<ListOfOffenseAdapter.MyViewHolder>()  {

    private var offenseHist : List<NotifBOffenseHist>? = null

    fun setOffenseHist(restaurantMenu1: List<NotifBOffenseHist>?){
        this.offenseHist = restaurantMenu1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_offense, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(offenseHist?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (offenseHist == null) {
            0
        } else {
            offenseHist?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val book_type: TextView = itemView.tvOffenseBookType
        private val dateTime: TextView = itemView.tvOffenseDateTime

        fun bind(data: NotifBOffenseHist){
            book_type.text = data.book_type
            dateTime.text = data.dateTime
        }
    }
}