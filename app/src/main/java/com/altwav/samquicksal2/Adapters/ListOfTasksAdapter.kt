package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.Tasks
import kotlinx.android.synthetic.main.list_of_tasks.view.*

class ListOfTasksAdapter : RecyclerView.Adapter<ListOfTasksAdapter.MyViewHolder>()  {

    private var restaurantTasks : List<Tasks>? = null
    fun setRestaurantTask(restaurantTasks1: List<Tasks>?){
        this.restaurantTasks = restaurantTasks1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_tasks, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurantTasks?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (restaurantTasks == null) {
            0
        } else {
            restaurantTasks?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val task: TextView = itemView.tvTask

        fun bind(data: Tasks){
            task.text = "\u2022 ${data.task}"
        }
    }
}