package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.RestaurantMenuModelResponse
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_of_order_sets.view.*

class ListOfOrderSetsAdapter: RecyclerView.Adapter<ListOfOrderSetsAdapter.MyViewHolder>()  {

    private var restaurantMenu : List<RestaurantMenuModelResponse>? = null

    fun setRestaurantMenu(restaurantMenu1: List<RestaurantMenuModelResponse>?){
        this.restaurantMenu = restaurantMenu1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_order_sets, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurantMenu?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (restaurantMenu == null) {
            0
        } else {
            restaurantMenu?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val orderSetName: TextView = itemView.tvOrderSetName
        private val orderSetTagline: TextView = itemView.tvOrderSetTagline
        private val orderSetDescription: TextView = itemView.tvOrderSetDecription
        private val orderSetPrice: TextView = itemView.tvOrderSetPrice
        private val orderSetImage: ImageView = itemView.ivOrderSetImage

        fun bind(data: RestaurantMenuModelResponse){
            orderSetName.text = data.orderSetName
            orderSetTagline.text = data.orderSetTagline
            orderSetDescription.text = data.orderSetDescription
            orderSetPrice.text = data.orderSetPrice
            Glide.with(itemView).load(data.orderSetImage).into(orderSetImage)

            val foodSetsAdapter = ListOfFoodSetsAdapter(data.foodSet!!)
            itemView.foodSetsRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.foodSetsRecyclerView.adapter = foodSetsAdapter

        }
    }
}