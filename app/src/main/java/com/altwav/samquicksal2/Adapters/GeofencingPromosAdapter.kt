package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.Mechanics
import com.altwav.samquicksal2.models.NFGPromosModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_of_mechanics.view.*
import kotlinx.android.synthetic.main.lists_geo_promos.view.*

class GeofencingPromosAdapter : RecyclerView.Adapter<GeofencingPromosAdapter.MyViewHolder>() {

    private var geoPromos : List<NFGPromosModel>? = null
    fun setGeoPromos(geoPromos1: List<NFGPromosModel>?){
        this.geoPromos = geoPromos1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lists_geo_promos, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(geoPromos?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (geoPromos == null) {
            0
        } else {
            geoPromos?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val promoTitle: TextView = itemView.tvGeoPromoTitle
        private val promoDescription: TextView = itemView.tvGeoPromoDescription
        private val promoImage: ImageView = itemView.ivGeoPromoImage

        fun bind(data: NFGPromosModel){
            promoTitle.text = data.promoTitle
            promoDescription.text = data.promoDescription
            Glide.with(itemView).load(data.promoImage).into(promoImage)
        }
    }
}