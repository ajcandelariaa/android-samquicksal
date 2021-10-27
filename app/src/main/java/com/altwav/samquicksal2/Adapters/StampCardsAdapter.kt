package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R

class StampCardsAdapter : RecyclerView.Adapter<StampCardsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_stamp_cards, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 9
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        init {
//            itemView.setOnClickListener {
//                val context = itemView.context
//                val intent = Intent(context, TransactionDetailsActivity::class.java)
//                context.startActivity(intent)
//            }
//        }
    }
}