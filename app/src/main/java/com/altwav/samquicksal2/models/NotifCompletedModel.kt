package com.altwav.samquicksal2.models

data class NotifCompletedModel(
    val restaurant: String? = null,
    val finalBookType: String? = null,
    val finalBookId: Int? = null,
    val stampCardId: Int? = null,
    val tasks: List<String>? = null,
    val countTasks: Int? = null,
)
