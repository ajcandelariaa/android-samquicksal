package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.FoodSet
import com.altwav.samquicksal2.models.RestaurantMenuModelResponse
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_of_food_sets.view.*
import kotlinx.android.synthetic.main.list_of_order_sets.view.*

class ListOfFoodSetsAdapter(item: List<FoodSet>) : RecyclerView.Adapter<ListOfFoodSetsAdapter.MyViewHolder>() {

    private var restaurantFoodSet: List<FoodSet>? = null

    init {
        this.restaurantFoodSet = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_food_sets, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurantFoodSet?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (restaurantFoodSet == null) {
            0
        } else {
            restaurantFoodSet?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val foodSetName: TextView = itemView.tvFoodSetName
        private val foodItems: TextView = itemView.tvFoodItem

        fun bind(data: FoodSet){
            var count = 1
            var foodItem = ""
            foodSetName.text = data.foodSetName
            for (item in data.foodItem!!){
                if(count == 1){
                    foodItem += "$count. $item"
                } else {
                    foodItem += "\n$count. $item"
                }
                count+=1
            }
            foodItems.text = foodItem
        }
    }
}