package com.altwav.samquicksal2.Adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.UpdateFoodItemInterface
import com.altwav.samquicksal2.models.CurrentOrdersModel
import com.altwav.samquicksal2.models.UpdateFoodItemModel
import kotlinx.android.synthetic.main.ordering_orders.view.*

class OrderingOrdersAdapter(updateFoodItem3: UpdateFoodItemInterface) : RecyclerView.Adapter<OrderingOrdersAdapter.MyViewHolder>() {

    private var currentOrders : List<CurrentOrdersModel>? = null
    private var updateFoodItem : UpdateFoodItemInterface = updateFoodItem3
    fun setCurrentOrders(currentOrders1: List<CurrentOrdersModel>?){
        this.currentOrders = currentOrders1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ordering_orders, parent, false)
        return MyViewHolder(view, updateFoodItem)
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

    class MyViewHolder(itemView: View, updateFoodItem2: UpdateFoodItemInterface): RecyclerView.ViewHolder(itemView) {
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

        init {
            itemView.btnCOFoodDelete.setOnClickListener {
                AlertDialog.Builder(itemView.context)
                    .setTitle("Remove item")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("Are you sure you want to remove this item?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        val foodItem = UpdateFoodItemModel(custLOrderId, "delete")
                        updateFoodItem2.updateItem(foodItem)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.cancel()
                    }
                    .show()
            }

            itemView.btnCOFoodPlus.setOnClickListener {
                val foodItem = UpdateFoodItemModel(custLOrderId, "add")
                updateFoodItem2.updateItem(foodItem)
            }

            itemView.btnCOFoodMinus.setOnClickListener {
                val foodItem = UpdateFoodItemModel(custLOrderId, "sub")
                updateFoodItem2.updateItem(foodItem)
            }
        }

    }
}