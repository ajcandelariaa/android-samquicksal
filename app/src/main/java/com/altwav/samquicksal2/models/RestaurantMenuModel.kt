package com.altwav.samquicksal2.models

data class RestaurantMenuModel(
    val id: Int? = null,
)

data class RestaurantMenuModelResponse(
    val description: String? = null,
    val status: String? = null,
    val menu: List<Menu>? = null,
)

data class Menu(
    val foodSet: List<FoodSet>? = null,
    val orderSetDescription: String? = null,
    val orderSetImage: String? = null,
    val orderSetName: String? = null,
    val orderSetPrice: String? = null,
    val orderSetTagline: String? = null,
)

data class FoodSet(
    val foodItem: List<String>? = null,
    val foodSetName: String? = null,
)
