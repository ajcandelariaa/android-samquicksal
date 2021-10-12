package com.altwav.samquicksal2.Adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantViewActivity
import com.altwav.samquicksal2.models.ListOfRestaurantModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.lists_of_restaurant.view.*
import android.os.Bundle
import com.altwav.samquicksal2.restaurantViewFragments.AboutFragment


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
        return if (restaurantList == null) {
            0
        } else {
            restaurantList?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val restaurantName: TextView = itemView.restaurantName
        private val restaurantAddress: TextView = itemView.restaurantAddress
        private val restaurantImage: CircleImageView = itemView.restaurantImage
        private val restaurantSchedule: TextView = itemView.restaurantSchedule
        private var restaurantId: Int? = null

        fun bind(data: ListOfRestaurantModel){
            restaurantId = data.id
            restaurantName.text = data.rName
            restaurantAddress.text = data.rAddress
            restaurantSchedule.text = data.rSchedule
            if(data.rSchedule == "Closed Now"){
                restaurantSchedule.setTextColor(Color.parseColor("#91001B"))
            } else {
                restaurantSchedule.setTextColor(Color.parseColor("#0AA034"))
            }
            Glide.with(itemView).load(data.rLogo).into(restaurantImage)
        }
        init {
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, RestaurantViewActivity::class.java)
                intent.putExtra("restaurantId", restaurantId)
                intent.putExtra("restaurantName", "${restaurantName.text}")
                context.startActivity(intent)
            }
        }
    }
}