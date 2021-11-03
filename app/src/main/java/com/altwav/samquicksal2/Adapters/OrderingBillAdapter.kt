package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.ListOfPromosModel
import com.altwav.samquicksal2.models.OrderingBillModel
import com.altwav.samquicksal2.models.OrderingBillOrders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.ordering_bill.view.*
import kotlinx.android.synthetic.main.ordering_orders.view.*

class OrderingBillAdapter : RecyclerView.Adapter<OrderingBillAdapter.MyViewHolder>() {

    private var orderingBillOrders : List<OrderingBillOrders>? = null
    fun setOrderingbill(orderingBillOrders1: List<OrderingBillOrders>?){
        this.orderingBillOrders = orderingBillOrders1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ordering_bill, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(orderingBillOrders?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (orderingBillOrders == null) {
            0
        } else {
            orderingBillOrders?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val foodName: TextView = itemView.tvCBillFoodName
        private val foodQuantity: TextView = itemView.tvCBillFoodQuantity
        private val foodPrice: TextView = itemView.tvCBillFoodPrice

        fun bind(data: OrderingBillOrders){
            foodName.text = data.foodItemName
            foodQuantity.text = "${data.quantity.toString()}x"
            foodPrice.text = data.price
        }
    }
}