package com.altwav.samquicksal2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.QueueFormActivity
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.ReservationFormActivity
import com.altwav.samquicksal2.models.OrderSet
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_choose_order_set.view.*
import kotlinx.android.synthetic.main.choose_order_set.view.*
import kotlinx.android.synthetic.main.list_of_mechanics.view.*

class ChooseOrderSetAdapter : RecyclerView.Adapter<ChooseOrderSetAdapter.MyViewHolder>() {
    private var chooseOrderSet : List<OrderSet>? = null
    private var restaurantId : Int? = null
    private var bookType : String? = null

    fun setChooseOrderSet(chooseOrderSet1: List<OrderSet>?, restaurantId1: Int, bookType1: String){
        this.chooseOrderSet = chooseOrderSet1
        this.restaurantId = restaurantId1
        this.bookType = bookType1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.choose_order_set, parent, false)
        return MyViewHolder(view, restaurantId!!, bookType!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(chooseOrderSet?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (chooseOrderSet == null) {
            0
        } else {
            chooseOrderSet?.size!!
        }
    }

    class MyViewHolder(itemView: View, restaurantId2: Int, bookType2: String): RecyclerView.ViewHolder(itemView) {
        private val orderSetName: TextView = itemView.tvChooseOrderSetName
        private val orderSetTagline: TextView = itemView.tvChooseOrderSetTagline
        private val orderSetImage: ImageView = itemView.ivChooseOrderSetImage
        private var orderSetId: Int? = null

        fun bind(data: OrderSet){
            orderSetName.text = data.orderSetName
            orderSetTagline.text = data.orderSetTagline
            orderSetId = data.orderSetId
            Glide.with(itemView).load(data.orderSetImage).into(orderSetImage)
        }

        init {
            itemView.setOnClickListener {
                var intent: Intent? = null

                if(bookType2 == "Reservation"){
                    intent = Intent(itemView.context, ReservationFormActivity::class.java)
                } else {
                    intent = Intent(itemView.context, QueueFormActivity::class.java)
                }
                intent.putExtra("orderSetId", orderSetId)
                intent.putExtra("restaurantId", restaurantId2)
                itemView.context.startActivity(intent)
            }
        }
    }
}