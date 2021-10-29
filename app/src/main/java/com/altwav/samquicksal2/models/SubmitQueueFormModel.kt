package com.altwav.samquicksal2.models

data class SubmitQueueFormModel(
    val customer_id: Int? = null,
    val restAcc_id: Int? = null,
    val orderSet_id: Int? = null,
    val numberOfPersons: Int? = null,
    val numberOfTables: Int? = null,
    val hoursOfStay: Int? = null,
    val numberOfChildren: Int? = null,
    val numberOfPwd: Int? = null,
    val totalPwdChild: Int? = null,
    val notes: String? = null,
    val rewardStatus: String? = null,
    val rewardType: String? = null,
    val rewardInput: Int? = null,
    val rewardClaimed: String? = null,
    val totalPrice: Double? = null,
)

data class SubmitQueueFormModelResponse(
    val status: String? = null
)
