package com.altwav.samquicksal2.models

data class RestaurantRewardPromoModel(
    val id: Int? = null,
)

data class RestaurantRewardPromoModelResponse(
    val stampCapacity: Int? = null,
    val stampReward: String? = null,
    val stampTasks: List<Tasks>? = null,
    val stampValidity: String? = null,
    val promos: List<Promo>? = null,
)

data class Tasks(
    val task: String? = null,
)

data class Promo(
    val promoId: Int,
    val promoImage: String? = null,
    val promoName: String? = null,
)