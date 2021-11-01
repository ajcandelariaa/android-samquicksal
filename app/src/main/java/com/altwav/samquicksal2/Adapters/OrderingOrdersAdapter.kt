package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.CurrentOrdersModel
import com.altwav.samquicksal2.models.OrderFoodSet
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.ordering_food_sets.view.*
import kotlinx.android.synthetic.main.ordering_orders.view.*

class OrderingOrdersAdapter : RecyclerView.Adapter<OrderingOrdersAdapter.MyViewHolder>() {

    private var currentOrders : List<CurrentOrdersModel>? = null
    fun setCurrentOrders(currentOrders1: List<CurrentOrdersModel>?){
        this.currentOrders = currentOrders1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ordering_orders, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentOrders?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (currentOrders == null) {
            0
        } else {
            currentOrders?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val foodName: TextView = itemView.tvCOFoodName
        private val foodQuantity: TextView = itemView.tvCOFoodQuantity
        private val foodPrice: TextView = itemView.tvCOFoodPrice
        private var custLOrderId: Int? = null

        fun bind(data: CurrentOrdersModel){
            foodName.text = data.foodItemName
            foodQuantity.text = data.quantity.toString()
            foodPrice.text = data.price
            custLOrderId = data.custLOrder_id
        }

    }
}