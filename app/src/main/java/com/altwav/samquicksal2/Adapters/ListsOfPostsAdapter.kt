package com.altwav.samquicksal2.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.ListOfRestaurantModel
import com.altwav.samquicksal2.models.RPosts
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.lists_of_posts.view.*
import kotlinx.android.synthetic.main.lists_of_restaurant.view.*

class ListsOfPostsAdapter : RecyclerView.Adapter<ListsOfPostsAdapter.MyViewHolder>() {

    private var restuarantPost : List<RPosts>? = null
    fun setRestaurantPost(restaurantPost1: List<RPosts>?){
        this.restuarantPost = restaurantPost1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lists_of_posts, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restuarantPost?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (restuarantPost == null) {
            0
        } else {
            restuarantPost?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val postDescription: TextView = itemView.tvPostDescription
        private val postImage: ImageView = itemView.ivPostImage

        fun bind(data: RPosts){
            postDescription.text = data.description
            Glide.with(itemView).load(data.image).into(postImage)
        }

    }
}