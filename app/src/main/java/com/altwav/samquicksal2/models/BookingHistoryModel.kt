package com.altwav.samquicksal2.models

data class BookingHistoryModel(
    val id: Int? = null,
)

data class BookingHistoryModelResponse(
    val bookId: Int? = null,
    val rLogo: String? = null,
    val rName: String? = null,
    val rAddress: String? = null,
    val bookDate: String? = null,
    val bookStatus: String? = null,
    val bookType: String? = null,
)
