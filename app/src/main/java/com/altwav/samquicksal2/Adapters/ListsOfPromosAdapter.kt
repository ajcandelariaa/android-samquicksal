package com.altwav.samquicksal2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantPromoViewActivity
import com.altwav.samquicksal2.models.ListOfPromosModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.lists_of_promos.view.*

class ListsOfPromosAdapter : RecyclerView.Adapter<ListsOfPromosAdapter.MyViewHolder>() {

    private var promoList : List<ListOfPromosModel>? = null
    fun setPromosList(promoList1: List<ListOfPromosModel>?){
        this.promoList = promoList1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lists_of_promos, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(promoList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (promoList == null) {
            0
        } else {
            promoList?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val promoTitle: TextView = itemView.tvListPromoTitle
        private val restaurantAddress: TextView = itemView.tvListPromoAddress
        private val promoValidity: TextView = itemView.tvListPromoValidity
        private val restaurantImage: CircleImageView = itemView.ivListPromoRestaurantImage
        private var restaurantId: Int? = null
        private var promoId: Int? = null

        fun bind(data: ListOfPromosModel){
            restaurantId = data.restaurantId
            promoId = data.promoId
            promoTitle.text = data.promoTitle
            restaurantAddress.text = data.restaurantAddress
            promoValidity.text = "${data.promoStartDate} - ${data.promoEndDate}"

            Glide.with(itemView).load(data.restaurantImage).into(restaurantImage)
        }
        init {
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, RestaurantPromoViewActivity::class.java)
                intent.putExtra("promoId", promoId)
                intent.putExtra("restaurantId", restaurantId)
                context.startActivity(intent)
            }
        }
    }
}