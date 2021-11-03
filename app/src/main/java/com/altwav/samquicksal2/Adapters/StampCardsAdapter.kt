package com.altwav.samquicksal2.Adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.StampDetailsActivity
import com.altwav.samquicksal2.TransactionDetailsActivity
import com.altwav.samquicksal2.models.OrderingBillOrders
import com.altwav.samquicksal2.models.StampCardsModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.list_of_stamp_cards.view.*
import kotlinx.android.synthetic.main.ordering_bill.view.*

class StampCardsAdapter : RecyclerView.Adapter<StampCardsAdapter.MyViewHolder>() {

    private var stampCards : List<StampCardsModel>? = null
    fun setStampCards(stampCards1: List<StampCardsModel>?){
        this.stampCards = stampCards1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_stamp_cards, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(stampCards?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (stampCards == null) {
            0
        } else {
            stampCards?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val stampReward: TextView = itemView.tvSCReward
        private val rAddress: TextView = itemView.tvSCRAddress
        private val stampValidity: TextView = itemView.tvSCValidity
        private val stampClaimed: TextView = itemView.tvSCRewardClaimed
        private val currentNumStamp: TextView = itemView.tvSCCurrentStamp
        private val stampCapacity: TextView = itemView.tvSCCapacity
        private val rLogo: CircleImageView = itemView.ivSCRLogo
        private var stamp_id: Int? = null

        fun bind(data: StampCardsModel){
            stampReward.text = data.stampReward
            rAddress.text = data.rAddress
            stampValidity.text = data.stampValidity
            stampClaimed.text = "Claimed: ${data.stampClaimed}"
            currentNumStamp.text = data.currentNumStamp.toString()
            stampCapacity.text = data.stampCapacity.toString()
            Glide.with(itemView).load(data.rLogo).into(rLogo)
            stamp_id = data.stamp_id

            if(data.stampClaimed == "Yes"){
                stampClaimed.setTextColor(Color.parseColor("#0AA034"))
            } else {
                stampClaimed.setTextColor(Color.parseColor("#91001B"))
            }
        }

        init {
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, StampDetailsActivity::class.java)
                intent.putExtra("stamp_id", stamp_id)
                context.startActivity(intent)
            }
        }
    }
}