package com.altwav.samquicksal2.models

data class NotifBlockedModel(
    val restaurant: String? = null,
    val description: String? = null,
    val description2: String? = null,
    val offenseHist: List<NotifBOffenseHist>? = null,
    val offenseType: String? = null,
)

data class NotifBOffenseHist(
    val book_type: String? = null,
    val dateTime: String? = null,
)
