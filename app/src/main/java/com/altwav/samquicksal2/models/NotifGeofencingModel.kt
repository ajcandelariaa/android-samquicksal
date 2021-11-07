package com.altwav.samquicksal2.models

data class NotifGeofencingModel(
    val restAcc_id: Int? = null,
    val rName: String? = null,
    val rBranch: String? = null,
    val rAddress: String? = null,
    val rLogo: String? = null,
    val promos: List<NFGPromosModel>? = null,
    val stampDiscount: String? = null,
    val stampValidity: String? = null,
    val stampCapacity: String? = null,
    val stampTasks: List<String>? = null,
    val orderSets: List<NFGOrderSetsModel>? = null,
)

data class NFGPromosModel(
    val promoImage: String? = null,
    val promoTitle: String? = null,
    val promoDescription: String? = null,
)

data class NFGOrderSetsModel(
    val orderSetName: String? = null,
    val orderSetTagline: String? = null,
    val orderSetDescription: String? = null,
    val orderSetPrice: String? = null,
    val orderSetImage: String? = null,
)
