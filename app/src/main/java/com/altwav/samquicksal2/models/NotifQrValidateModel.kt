package com.altwav.samquicksal2.models

data class NotifQrValidateModel(
    val custQrAcces_id: Int? = null,
    val custName: String? = null,
    val custEAddress: String? = null,
    val custENumber: String? = null,
    val scannedStatus: String? = null,
    val scanReqDate: String? = null,
    val scanReqTime: String? = null,
    val tableNumbers: List<String>? = null,
)
