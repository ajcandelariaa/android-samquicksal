package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.NFGOrderSetsModel
import com.altwav.samquicksal2.models.NFGPromosModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_geo_menu.view.*
import kotlinx.android.synthetic.main.lists_geo_promos.view.*

class GeofencingMenuAdapter : RecyclerView.Adapter<GeofencingMenuAdapter.MyViewHolder>() {

    private var geoMenu : List<NFGOrderSetsModel>? = null
    fun setGeoMenu(geoMenu1: List<NFGOrderSetsModel>?){
        this.geoMenu = geoMenu1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_geo_menu, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(geoMenu?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (geoMenu == null) {
            0
        } else {
            geoMenu?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val orderSetName: TextView = itemView.tvGeoOSName
        private val orderSetTagline: TextView = itemView.tvGeoOSTagline
        private val orderSetDescription: TextView = itemView.tvGeoOSDescription
        private val orderSetPrice: TextView = itemView.tvGeoOSPrice
        private val orderSetImage: ImageView = itemView.ivGeoOSImage


        fun bind(data: NFGOrderSetsModel){
            orderSetName.text = data.orderSetName
            orderSetTagline.text = data.orderSetTagline
            orderSetDescription.text = data.orderSetDescription
            orderSetPrice.text = "Price: ${data.orderSetPrice}"
            Glide.with(itemView).load(data.orderSetImage).into(orderSetImage)
        }
    }
}