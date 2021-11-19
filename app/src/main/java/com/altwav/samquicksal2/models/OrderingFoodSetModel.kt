package com.altwav.samquicksal2.models

data class OrderingFoodSetModel(
    val id: Int? = null,
)


data class OrderingFoodSetModelResponse(
    val restAccId: Int? = null,
    val orderSetId: Int? = null,
    val foodSets: List<OrderFoodSet>? = null,
)

data class OrderFoodSet(
    val foodSetId: Int? = null,
    val foodSetImage: String? = null,
    val foodSetName: String? = null,
    val foodSetDescription: String? = null,
    val foodSetAvailable: String? = null,
)
