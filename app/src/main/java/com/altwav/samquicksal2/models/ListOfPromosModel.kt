package com.altwav.samquicksal2.models

data class ListOfPromosModel(
    val restaurantImage: String? = null,
    val restaurantAddress: String? = null,
    val restaurantId: Int? = null,
    val promoId: Int? = null,
    val promoTitle: String? = null,
    val promoStartDate: String? = null,
    val promoEndDate: String? = null,
)
