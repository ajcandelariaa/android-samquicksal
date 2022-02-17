package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.RPolicy
import com.altwav.samquicksal2.models.RUnavailableD
import kotlinx.android.synthetic.main.list_of_restaurant_policy.view.*
import kotlinx.android.synthetic.main.list_of_unavailabled.view.*

class UnavailableDAdapter : RecyclerView.Adapter<UnavailableDAdapter.MyViewHolder>() {

    private var unavailableDate : List<RUnavailableD>? = null
    fun setUnavailableDate(unavailableDate1: List<RUnavailableD>?){
        this.unavailableDate = unavailableDate1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_unavailabled, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(unavailableDate?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (unavailableDate == null) {
            0
        } else {
            unavailableDate?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.tvUDDate
        private val description: TextView = itemView.tvUDDesc
        private val startTime: TextView = itemView.tvUDTime

        fun bind(data: RUnavailableD){
            date.text = data.date
            description.text = data.description
            startTime.text = data.startTime
        }
    }
}