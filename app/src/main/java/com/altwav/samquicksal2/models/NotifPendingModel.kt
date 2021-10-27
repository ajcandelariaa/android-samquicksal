package com.altwav.samquicksal2.models

data class NotifPendingModel(
    val orderName: String? = null,
    val numberOfPersons: Int? = null,
    val numberOfTables: Int? = null,
    val hoursOfStay: Int? = null,
    val numberOfChildren: Int? = null,
    val numberOfPwd: Int? = null,
    val note: String? = null,
    val restaurant: String? = null,
    val statusViewable: String? = null,
)