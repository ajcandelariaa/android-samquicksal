package com.altwav.samquicksal2.models

data class RestoReviewModel(
    val averageRating: String? = null,
    val countReviews: Int? = null,
    val custReviews: List<RestoCustReviewList>? = null,
)

data class RestoCustReviewList(
    val custImage: String? = null,
    val custName: String? = null,
    val custRating: String? = null,
    val custComment: String? = null,
    val custRateDate: String? = null,
)