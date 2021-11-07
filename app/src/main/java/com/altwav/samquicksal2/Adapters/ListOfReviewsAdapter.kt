package com.altwav.samquicksal2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.RestoCustReviewList
import com.altwav.samquicksal2.models.StampDoneTasks
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.lists_of_reviews.view.*
import kotlinx.android.synthetic.main.stamp_card_tasks.view.*

class ListOfReviewsAdapter : RecyclerView.Adapter<ListOfReviewsAdapter.MyViewHolder>() {

    private var custRestoReview : List<RestoCustReviewList>? = null
    fun setCustRestReview(custRestoReview1: List<RestoCustReviewList>?){
        this.custRestoReview = custRestoReview1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lists_of_reviews, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(custRestoReview?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (custRestoReview == null) {
            0
        } else {
            custRestoReview?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val custName: TextView = itemView.tvRRCustName
        private val custRateDate: TextView = itemView.tvRRCustDate
        private val custComment: TextView = itemView.tvRRCustComment
        private val custRatingLabel: TextView = itemView.tvRRStarLabel
        private val custRating: RatingBar = itemView.rbRRStar
        private val custImage: CircleImageView = itemView.ivRRCustImage


        fun bind(data: RestoCustReviewList){
            custName.text = data.custName
            custRateDate.text = data.custRateDate
            custComment.text = data.custComment
            custRatingLabel.text = "(${data.custRating})"
            Glide.with(itemView).load(data.custImage).into(custImage)
            custRating.rating = data.custRating!!.toFloat()
        }
    }
}