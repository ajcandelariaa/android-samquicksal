package com.altwav.samquicksal2.models

data class QRScannedModel(
    val custId: Int? = null,
    val custImage: String? = null,
    val custName: String? = null,
    val custEAddress: String? = null,
    val custCNumber: String? = null,
    val custJDate: String? = null,
    val bdRName: String? = null,
    val bdRAddres: String? = null,
    val bdOrderName: String? = null,
    val bdBookType: String? = null,
    val bdAccessSlot: Int? = null,
    val reqStatus: String? = null,
)
