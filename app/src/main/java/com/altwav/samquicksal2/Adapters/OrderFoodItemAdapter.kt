package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.AddFoodItemInterface
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.AddFooditemModel
import com.altwav.samquicksal2.models.OrderingFoodItemModelResponse
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.ordering_food_items.view.*

class OrderFoodItemAdapter(addFoodItem3: AddFoodItemInterface) : RecyclerView.Adapter<OrderFoodItemAdapter.MyViewHolder>() {

    private var orderingFoodItem : List<OrderingFoodItemModelResponse>? = null
    private var customerId : Int? = null
    private var addFoodItem : AddFoodItemInterface = addFoodItem3

    fun setOrderingFoodItem(orderingFoodItem1: List<OrderingFoodItemModelResponse>?, customerId1: Int){
        this.orderingFoodItem = orderingFoodItem1
        this.customerId = customerId1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ordering_food_items, parent, false)
        return MyViewHolder(view, customerId!!, addFoodItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(orderingFoodItem?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (orderingFoodItem == null) {
            0
        } else {
            orderingFoodItem?.size!!
        }
    }

    class MyViewHolder(itemView: View, customerId2: Int, addFoodItem2: AddFoodItemInterface): RecyclerView.ViewHolder(itemView) {
        private val foodItemName: TextView = itemView.tvOrderingFIName
        private val foodItemDesc: TextView = itemView.tvOrderingFIDesc
        private val foodItemImage: ImageView = itemView.ivOrderingFIImage
        private var foodItemType: String? = null
        private var foodItemPrice: String? = null
        private var foodItemId: Int? = null
        private var foodItemAvailable: String? = null

        fun bind(data: OrderingFoodItemModelResponse){
            foodItemName.text = data.foodItemName
            foodItemDesc.text = data.foodItemDescription
            Glide.with(itemView).load(data.foodItemImage).into(foodItemImage)
            foodItemType = data.foodItemType
            foodItemPrice = data.foodItemPrice
            foodItemId = data.foodItemId
            foodItemAvailable = data.foodItemAvailable


            if(foodItemAvailable == "No"){
                foodItemName.visibility = View.GONE
                foodItemDesc.text = "Not Available"
            }
        }

        init {
            itemView.setOnClickListener{
                if(foodItemAvailable == "Yes"){
                    val foodItem = AddFooditemModel(customerId2, foodItemId!!, foodItemName.text.toString(), foodItemPrice!!)
                    addFoodItem2.addItem(foodItem)
                }
            }
        }
    }
}