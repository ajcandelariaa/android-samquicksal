package com.altwav.samquicksal2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantViewActivity
import com.altwav.samquicksal2.models.RatedRestaurantsModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.rated_restaurants.view.*

class RatedRestaurantsAdapter : RecyclerView.Adapter<RatedRestaurantsAdapter.MyViewHolder>() {

    private var ratedRestaurants : List<RatedRestaurantsModel>? = null
    fun setRatedRestaurants(ratedRestaurants1: List<RatedRestaurantsModel>?){
        this.ratedRestaurants = ratedRestaurants1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rated_restaurants, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(ratedRestaurants?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (ratedRestaurants == null) {
            0
        } else {
            ratedRestaurants?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val rName: TextView = itemView.tvRatedRestaurantName
        private val rAddress: TextView = itemView.tvRatedBranchName
        private val rRating: RatingBar = itemView.rbRatedRestaurantStar
        private val rRatingLabel: TextView = itemView.tvRatedRestaurantStarLabel
        private val rCountReview: TextView = itemView.tvRatedRestaurantCount
        private val rLogo: ImageView = itemView.ivRatedRestaurantImage
        private var restId: Int? = null

        fun bind(data: RatedRestaurantsModel){
            rName.text = data.rName
            rAddress.text = data.rAddress
            rRating.rating = data.rRating!!.toFloat()
            rRatingLabel.text = "(${data.rRating})"
            rCountReview.text = "(${data.rCountReview})"
            Glide.with(itemView).load(data.rLogo).into(rLogo)
            restId = data.restId
        }
        init {
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, RestaurantViewActivity::class.java)
                intent.putExtra("restaurantId", restId)
                intent.putExtra("restaurantName", "${rName.text}")
                context.startActivity(intent)
            }
        }
    }
}