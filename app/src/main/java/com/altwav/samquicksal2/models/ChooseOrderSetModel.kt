package com.altwav.samquicksal2.models

data class ChooseOrderSetModel(
    val id: Int? = null,
)

data class ChooseOrderSetModelResponse(
    val restaurantName: String? = null,
    val rTimeLimit: Int? = null,
    val rCapacityPerTable: Int? = null,
    val rewardStatus: String? = null,
    val rewardType: String? = null,
    val rewardInput: Int? = null,
    val orderSets: List<OrderSet>? = null,
)

data class OrderSet(
    val orderSetId: Int? = null,
    val orderSetPrice: String? = null,
    val orderSetImage: String? = null,
    val orderSetName: String? = null,
    val orderSetTagline: String? = null,
)
