package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.Mechanics
import kotlinx.android.synthetic.main.list_of_mechanics.view.*

class ListOfMechanicsAdapter : RecyclerView.Adapter<ListOfMechanicsAdapter.MyViewHolder>() {

    private var promoMechanics : List<Mechanics>? = null
    fun setPromoMechanics(promoMechanics1: List<Mechanics>?){
        this.promoMechanics = promoMechanics1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_mechanics, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(promoMechanics?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (promoMechanics == null) {
            0
        } else {
            promoMechanics?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val mechanic: TextView = itemView.tvPromoDetailMechanic

        fun bind(data: Mechanics){
            mechanic.text = "\u2022 ${data.mechanic}"
        }
    }
}