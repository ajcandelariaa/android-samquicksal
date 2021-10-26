package com.altwav.samquicksal2.models

data class LiveStatusModel(
    val id: Int? = null
)


data class LiveStatusModelResponse(
    val liveStatusHeader: String? = null,
    val liveStatusBody: String? = null,
    val liveStatusNumber: Int? = null,
    val liveStatusNumberDesc: String? = null,
    val liveStatusDescription: String? = null,
)