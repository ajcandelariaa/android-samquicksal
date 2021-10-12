package com.altwav.samquicksal2.Adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantViewActivity
import com.altwav.samquicksal2.models.ListOfRestaurantModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.lists_of_restaurant.view.*

class ListsOfRestaurantAdapter : RecyclerView.Adapter<ListsOfRestaurantAdapter.MyViewHolder>() {

    private var restaurantList : List<ListOfRestaurantModel>? = null
    fun setRestaurantList(restaurantList: List<ListOfRestaurantModel>?){
        this.restaurantList = restaurantList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lists_of_restaurant, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurantList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if (restaurantList == null) {
            return 0
        } else {
            return restaurantList?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val restaurantName = itemView.restaurantName
        val restaurantAddress = itemView.restaurantAddress
        val restaurantImage = itemView.restaurantImage
        val restaurantSchedule = itemView.restaurantSchedule

//        @SuppressLint("ResourceAsColor")
        fun bind(data: ListOfRestaurantModel){
            restaurantName.text = data.rName
            restaurantAddress.text = data.rAddress
            restaurantSchedule.text = data.rSchedule
//            if(data.rSchedule == "Closed Today"){
//                restaurantSchedule.setTextColor(R.color.restaurantOpeningTextColor)
//            } else {
//                restaurantSchedule.setTextColor(R.color.menuTextColor)
//            }
            Glide.with(itemView).load(data.rLogo).into(restaurantImage)
        }
        init {
            //https://medium.com/inside-ppl-b7/recyclerview-inside-fragment-with-android-studio-680cbed59d84
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, RestaurantViewActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}