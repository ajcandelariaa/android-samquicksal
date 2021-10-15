package com.altwav.samquicksal2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantPromoViewActivity
import com.altwav.samquicksal2.models.Promo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.lists_of_particular_promos.view.*

class ListsOfParticularPromosAdapter : RecyclerView.Adapter<ListsOfParticularPromosAdapter.MyViewHolder>() {

    private var restaurantParticularPromo : List<Promo>? = null
    fun setRestaurantParticularPromo(restaurantParticularPromo1: List<Promo>?){
        this.restaurantParticularPromo = restaurantParticularPromo1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lists_of_particular_promos, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurantParticularPromo?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (restaurantParticularPromo == null) {
            0
        } else {
            restaurantParticularPromo?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val promoName: TextView = itemView.tvPromoName
        private val promoImage: ImageView = itemView.ivPromoImage
        private var promoId: Int? = null

        fun bind(data: Promo){
            promoId = data.promoId
            promoName.text = data.promoName
            Glide.with(itemView).load(data.promoImage).into(promoImage)
        }

        init {
            itemView.btnPromoDetails.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, RestaurantPromoViewActivity::class.java)
                intent.putExtra("promoId", promoId)
                context.startActivity(intent)
            }
        }
    }
}