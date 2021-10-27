package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.RPolicy
import kotlinx.android.synthetic.main.list_of_restaurant_policy.view.*

class RestaurantPolicyAdapter : RecyclerView.Adapter<RestaurantPolicyAdapter.MyViewHolder>() {

    private var restaurantPolicy : List<RPolicy>? = null
    fun setRestaurantPolicy(restaurantPolicy1: List<RPolicy>?){
        this.restaurantPolicy = restaurantPolicy1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_restaurant_policy, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurantPolicy?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (restaurantPolicy == null) {
            0
        } else {
            restaurantPolicy?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val policy: TextView = itemView.tvRestaurantPolicy

        fun bind(data: RPolicy){
            policy.text = data.policy
        }
    }
}