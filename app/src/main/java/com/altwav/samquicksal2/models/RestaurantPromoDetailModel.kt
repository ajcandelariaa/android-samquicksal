package com.altwav.samquicksal2.models

data class RestaurantPromoDetailModel(
    val promoId: Int? = null,
    val restaurantId: Int? = null,
)

data class RestaurantPromoDetailModelResponse(
    val promoTitle: String? = null,
    val promoDescription: String? = null,
    val promoStartDate: String? = null,
    val promoEndDate: String? = null,
    val promoImage: String? = null,
    val promoMechanics: List<Mechanics>? = null,
)

data class Mechanics(
    val mechanic: String? = null,
)
