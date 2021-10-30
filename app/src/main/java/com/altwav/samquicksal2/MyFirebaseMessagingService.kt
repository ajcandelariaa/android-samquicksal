package com.altwav.samquicksal2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.altwav.samquicksal2.notificationsActivity.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val channelId = "notfication_channel"
private const val channelName = "com.altwav.samquicksal2"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private fun generateNotification(title: String, message: String, notificationType: String, notificationId: Int){
        var intent: Intent? = null
        when(notificationType){
            "New Account" -> {
                intent = Intent(this, NewAccountNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Geofencing" -> {
                intent = Intent(this, GeofencingNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Pending" -> {
                intent = Intent(this, PendingNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Cancelled" -> {
                intent = Intent(this, CancelledNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Declined" -> {
                intent = Intent(this, DeclinedNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Approved" -> {
                intent = Intent(this, ApprovedNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Validation" -> {
                intent = Intent(this, LiveStatusActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "No Show" -> {
                intent = Intent(this, NoShowNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Runaway" -> {
                intent = Intent(this, RunawayNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "QR Request" -> {
                intent = Intent(this, QRScannedNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Complete" -> {
                intent = Intent(this, CheckoutNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Blocked" -> {
                intent = Intent(this, BlockedNotificationActivity::class.java)
                intent.putExtra("notificationId", notificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            "Table Setting Up" -> {
                intent = Intent(this, LiveStatusActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }


        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)

        //channel id , channel name
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.samquicksal_logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationId = Random.nextInt()
        notificationManager.notify(notificationId, builder.build())
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.notification != null){
            generateNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!,
                remoteMessage.data["notificationType"]!!,
                remoteMessage.data["notificationId"]!!.toInt()
            )
        }
    }

    override fun onNewToken(deviceToken: String) {
        super.onNewToken(deviceToken)
        if(deviceToken != null){
            MainActivity().sendRegistrationToServer(deviceToken)
        }
    }


}