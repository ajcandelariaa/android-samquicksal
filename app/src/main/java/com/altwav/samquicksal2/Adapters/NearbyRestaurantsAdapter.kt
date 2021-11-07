package com.altwav.samquicksal2.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.NearbyRestoModelResponse
import com.altwav.samquicksal2.models.OrderingBillOrders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.nearby_restaurants.view.*
import kotlinx.android.synthetic.main.ordering_bill.view.*

class NearbyRestaurantsAdapter : RecyclerView.Adapter<NearbyRestaurantsAdapter.MyViewHolder>() {

    private var nearbyRestaurants : List<NearbyRestoModelResponse>? = null
    fun setNearbyResto(nearbyRestaurants1: List<NearbyRestoModelResponse>?){
        this.nearbyRestaurants = nearbyRestaurants1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nearby_restaurants, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(nearbyRestaurants?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (nearbyRestaurants == null) {
            0
        } else {
            nearbyRestaurants?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val rName: TextView = itemView.tvListNearbyRestaurantTitle
        private val rAddress: TextView = itemView.tvListNearbyRestaurantAddress
        private val rSchedule: TextView = itemView.tvListNearbyRestaurantSchedule
        private val distance: TextView = itemView.tvListNearbyRestaurantDistance
        private val rLogo: ImageView = itemView.ivListNearbyRestaurantImage
        private var restId: Int? = null

        fun bind(data: NearbyRestoModelResponse){
            rName.text = data.rName
            rAddress.text = data.rAddress
            rSchedule.text = data.rSchedule
            distance.text = data.distance
            Glide.with(itemView).load(data.rLogo).into(rLogo)
            restId = data.rest_id

            if(rSchedule.text == "Closed Now"){
                rSchedule.setTextColor(Color.parseColor("#91001B"))
            } else {
                rSchedule.setTextColor(Color.parseColor("#0AA034"))
            }
        }
    }
}