package com.altwav.samquicksal2.models

data class UploadReceiptModel(
    val cust_id: Int? = null,
    val gcashReceipt: String? = null,
)


data class UploadReceiptModelResponse(
    val status: String? = null,
)