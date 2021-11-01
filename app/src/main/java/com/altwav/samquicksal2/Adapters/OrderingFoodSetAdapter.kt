package com.altwav.samquicksal2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.OrderingFoodItemActivity
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.OrderFoodSet
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.ordering_food_sets.view.*

class OrderingFoodSetAdapter : RecyclerView.Adapter<OrderingFoodSetAdapter.MyViewHolder>() {

    private var orderingFoodSet : List<OrderFoodSet>? = null
    private var restAccId : Int? = null
    private var orderSetId : Int? = null
    fun setOrderingFoodSet(orderingFoodSet1: List<OrderFoodSet>?, restAccId1: Int, orderSetId1: Int){
        this.orderingFoodSet = orderingFoodSet1
        this.restAccId = restAccId1
        this.orderSetId = orderSetId1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ordering_food_sets, parent, false)
        return MyViewHolder(view, restAccId!!, orderSetId!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(orderingFoodSet?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (orderingFoodSet == null) {
            0
        } else {
            orderingFoodSet?.size!!
        }
    }

    class MyViewHolder(itemView: View, restAccId2: Int, orderSetId2: Int): RecyclerView.ViewHolder(itemView) {
        private val foodSetName: TextView = itemView.tvOrderingFSName
        private val foodSetDesc: TextView = itemView.tvOrderingFSDesc
        private val foodSetImage: ImageView = itemView.ivOrderingFSImage
        private var foodSetId: Int? = null

        fun bind(data: OrderFoodSet){
            foodSetName.text = data.foodSetName
            foodSetDesc.text = data.foodSetDescription
            Glide.with(itemView).load(data.foodSetImage).into(foodSetImage)
            foodSetId = data.foodSetId
        }

        init {
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, OrderingFoodItemActivity::class.java)
                intent.putExtra("restAccId", restAccId2)
                intent.putExtra("orderSetId", orderSetId2)
                intent.putExtra("foodSetId", foodSetId)
                intent.putExtra("foodSetName", foodSetName.text)
                context.startActivity(intent)
//                 OrderingFoodSetAdapter().notifyDataSetChanged()
            }
        }
    }

}