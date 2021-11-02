package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.Mechanics
import com.altwav.samquicksal2.models.OrderAsstHistModel
import kotlinx.android.synthetic.main.list_of_mechanics.view.*
import kotlinx.android.synthetic.main.ordering_assistance.view.*

class OrderingAssistanceAdapter : RecyclerView.Adapter<OrderingAssistanceAdapter.MyViewHolder>() {

    private var orderAssistHist : List<OrderAsstHistModel>? = null
    fun setOrderAssistHist(orderAssistHist1: List<OrderAsstHistModel>?){
        this.orderAssistHist = orderAssistHist1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ordering_assistance, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(orderAssistHist?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (orderAssistHist == null) {
            0
        } else {
            orderAssistHist?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val requestType: TextView = itemView.tvCOARequestType
        private val requestTime: TextView = itemView.tvCOARequestTime

        fun bind(data: OrderAsstHistModel){
            requestType.text = data.requestType
            requestTime.text = data.requestTime
        }
    }
}