package com.altwav.samquicksal2.models

data class BHCancelledModel(
    val cust_id: Int? = null,
    val book_id: Int? = null,
    val book_type: String? = null,
)


data class BHCancelledModelResponse(
    val bookDate: String? = null,
    val bookTime: String? = null,
    val rName: String? = null,
    val rAddress: String? = null,
    val orderName: String? = null,
    val hoursOfStay: String? = null,
    val numberOfPwd: Int? = null,
    val numberOfChildren: Int? = null,
    val reward: String? = null,
    val rewardClaimed: String? = null,
    val bookingType: String? = null,
    val notes: String? = null,
    val reason: String? = null,
)
