package com.altwav.samquicksal2.Adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.LiveStatusActivity
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantViewActivity
import com.altwav.samquicksal2.models.ListOfRestaurantModel
import com.altwav.samquicksal2.models.NotificationListModelResponse
import com.altwav.samquicksal2.notificationsActivity.*
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.list_of_notifications.view.*
import kotlinx.android.synthetic.main.lists_of_restaurant.view.*

class ListOfNotificationsAdapter : RecyclerView.Adapter<ListOfNotificationsAdapter.MyViewHolder>() {

    private var notificationList : List<NotificationListModelResponse>? = null
    fun setNotificationList(notificationList1: List<NotificationListModelResponse>?){
        this.notificationList = notificationList1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_notifications, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(notificationList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (notificationList == null) {
            0
        } else {
            notificationList?.size!!
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val notificationTitle: TextView = itemView.tvNotificationListTitle
        private val notificationDescription: TextView = itemView.tvNotificationListDescription
        private val notificationTime: TextView = itemView.tvNotificationListTime
        private val notificationImage: CircleImageView = itemView.ivNotificationListImage
        private val notificationBackground: ConstraintLayout = itemView.clNotificationListBackground
        private var notificationId: Int? = null
        private var notificationType: String? = null

        fun bind(data: NotificationListModelResponse){
            notificationId = data.id
            notificationType = data.notificationType
            notificationTitle.text = data.notificationTitle
            notificationDescription.text = data.notificationDescription
            notificationTime.text = data.notificationTime
            if(data.notificationStatus == "Unread"){
                notificationBackground.setBackgroundColor(Color.parseColor("#E5EFF5"))
            } else {
                notificationBackground.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            Glide.with(itemView).load(data.notificationImage).into(notificationImage)
        }
        init {
            itemView.setOnClickListener {
                val context = itemView.context
                when(notificationType){
                    "New Account" -> {
                        val intent = Intent(context, NewAccountNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Geofencing" -> {
                        val intent = Intent(context, GeofencingNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Pending" -> {
                        val intent = Intent(context, PendingNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Cancelled" -> {
                        val intent = Intent(context, CancelledNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Declined" -> {
                        val intent = Intent(context, DeclinedNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Approved" -> {
                        val intent = Intent(context, ApprovedNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Validation" -> {
                        val intent = Intent(context, LiveStatusActivity::class.java)
                        context.startActivity(intent)
                    }
                    "No Show" -> {
                        val intent = Intent(context, NoShowNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Runaway" -> {
                        val intent = Intent(context, RunawayNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "QR Request" -> {
                        val intent = Intent(context, QRScannedNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Complete" -> {
                        val intent = Intent(context, CheckoutNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Blocked" -> {
                        val intent = Intent(context, BlockedNotificationActivity::class.java)
                        intent.putExtra("notificationId", notificationId)
                        context.startActivity(intent)
                    }
                    "Table Setting Up" -> {
                        val intent = Intent(context, LiveStatusActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}