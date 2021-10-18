package com.altwav.samquicksal2.models

data class ChooseOrderSetModel(
    val id: Int? = null,
)

data class ChooseOrderSetModelResponse(
    val restaurantName: String? = null,
    val orderSets: List<OrderSet>? = null,
)

data class OrderSet(
    val orderSetId: Int? = null,
    val orderSetImage: String? = null,
    val orderSetName: String? = null,
    val orderSetTagline: String? = null,
)
