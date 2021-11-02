package com.altwav.samquicksal2.models

data class UpdateFoodItemModel(
    val custLOrder_id: Int? = null,
    val updateType: String? = null,
)

data class UpdateFoodItemModelResponse(
    val status: String? = null,
)