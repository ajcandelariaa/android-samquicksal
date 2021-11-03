package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.RPolicy
import com.altwav.samquicksal2.models.StampDoneTasks
import kotlinx.android.synthetic.main.list_of_restaurant_policy.view.*
import kotlinx.android.synthetic.main.stamp_card_tasks.view.*

class StampTasksAdapter : RecyclerView.Adapter<StampTasksAdapter.MyViewHolder>() {

    private var stampTasksDone : List<StampDoneTasks>? = null
    fun setStampTasksDone(stampTasksDone1: List<StampDoneTasks>?){
        this.stampTasksDone = stampTasksDone1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stamp_card_tasks, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(stampTasksDone?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (stampTasksDone == null) {
            0
        } else {
            stampTasksDone?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView = itemView.tvSCDTaskDoneName
        private val taskDate: TextView = itemView.tvSCDTaskDoneDate

        fun bind(data: StampDoneTasks){
            taskName.text = data.taskName
            taskDate.text = data.taskDate
        }
    }
}