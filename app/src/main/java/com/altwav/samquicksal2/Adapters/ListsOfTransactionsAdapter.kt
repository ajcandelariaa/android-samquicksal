package com.altwav.samquicksal2.Adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.*
import com.altwav.samquicksal2.models.BookingHistoryModelResponse
import com.altwav.samquicksal2.models.ListOfRestaurantModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.lists_of_restaurant.view.*
import kotlinx.android.synthetic.main.lists_of_transactions.view.*


class ListsOfTransactionsAdapter : RecyclerView.Adapter<ListsOfTransactionsAdapter.MyViewHolder>() {

    private var bookHisList : List<BookingHistoryModelResponse>? = null
    fun setBookHistory(bookHisList1: List<BookingHistoryModelResponse>?){
        this.bookHisList = bookHisList1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lists_of_transactions, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(bookHisList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (bookHisList == null) {
            0
        } else {
            bookHisList?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val rName: TextView = itemView.tvBHLRRName
        private val rAddress: TextView = itemView.tvBHLRAddress
        private val bookDate: TextView = itemView.tvBHLDate
        private val bookStatus: TextView = itemView.tvBHLType
        private val rLogo: CircleImageView = itemView.ivBHLRLogo
        private var bookId: Int? = null
        private var bookType: String? = null

        fun bind(data: BookingHistoryModelResponse){
            rName.text = data.rName
            rAddress.text = data.rAddress
            bookDate.text = data.bookDate
            bookId = data.bookId
            bookType = data.bookType
            Glide.with(itemView).load(data.rLogo).into(rLogo)

            when(data.bookStatus){
                "cancelled" -> {
                    bookStatus.text = "Cancelled"
                }
                "declined" -> {
                    bookStatus.text = "Declined"
                }
                "noShow" -> {
                    bookStatus.text = "No Show"
                }
                "runaway" -> {
                    bookStatus.text = "Runaway"
                }
                "completed" -> {
                    bookStatus.text = "Completed"
                }
            }
        }

        init {
            itemView.setOnClickListener {
                val context = itemView.context
                when(bookStatus.text){
                    "Cancelled" -> {
                        val intent = Intent(context, BdCancelledActivity::class.java)
                        intent.putExtra("bookId", bookId)
                        intent.putExtra("bookType", bookType)
                        context.startActivity(intent)
                    }
                    "Declined" -> {
                        val intent = Intent(context, BdDeclinedActivity::class.java)
                        intent.putExtra("bookId", bookId)
                        intent.putExtra("bookType", bookType)
                        context.startActivity(intent)
                    }
                    "No Show" -> {
                        val intent = Intent(context, BdNoshowActivity::class.java)
                        intent.putExtra("bookId", bookId)
                        intent.putExtra("bookType", bookType)
                        context.startActivity(intent)
                    }
                    "Runaway" -> {
                        val intent = Intent(context, BdRunawayActivity::class.java)
                        intent.putExtra("bookId", bookId)
                        intent.putExtra("bookType", bookType)
                        context.startActivity(intent)
                    }
                    "Completed" -> {
                        val intent = Intent(context, BdCompleteActivity::class.java)
                        intent.putExtra("bookId", bookId)
                        intent.putExtra("bookType", bookType)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}