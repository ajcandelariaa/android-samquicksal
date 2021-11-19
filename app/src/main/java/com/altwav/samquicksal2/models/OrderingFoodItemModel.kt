package com.altwav.samquicksal2.models

data class OrderingFoodItemModel(
    val restAcc_id: Int? = null,
    val orderSet_id: Int? = null,
    val foodSet_id: Int? = null,
)


data class OrderingFoodItemModelResponse(
    val foodItemId: Int? = null,
    val foodItemName: String? = null,
    val foodItemDescription: String? = null,
    val foodItemPrice: String? = null,
    val foodItemImage: String? = null,
    val foodItemType: String? = null,
    val foodItemAvailable: String? = null,
)
