package com.altwav.samquicksal2.models

data class QrReqAppDecModel(
    val custQrAcces_id: Int? = null,
    val reqType: String? = null,
    val tableNumber: String? = null,
)

data class QrReqAppDecModelResponse(
    val status: String? = null,
)
