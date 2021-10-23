package com.altwav.samquicksal2.models

data class NotificationListModel(
    val id: Int? = null,
)

data class NotificationListModelResponse(
    val id: Int? = null,
    val notificationType: String? = null,
    val notificationImage: String? = null,
    val notificationTitle: String? = null,
    val notificationDescription: String? = null,
    val notificationStatus: String? = null,
    val notificationTime: String? = null,
)
