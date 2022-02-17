    package com.altwav.samquicksal2.models

data class RestaurantAboutModel(
    val id: Int? = null,
)

data class RestaurantModelResponse(
    val rName: String? = null,
    val rAddress: String? = null,
    val rImage: String? = null,
    val rSchedule: List<RSchedule>? = null,
    val rTableCapacity: Int? = null,
    val rTableStatus: Int? = null,
    val rReservedTables: Int? = null,
    val rNumberOfPeople: Int? = null,
    val rNumberOfQueues: Int? = null,
    val rPosts: List<RPosts>? = null,
    val rPolicy: List<RPolicy>? = null,
    val rUnavailableD: List<RUnavailableD>? = null,
)

data class RSchedule(
    val Closing: String? = null,
    val Day: String? = null,
    val Opening: String
)

data class RPosts(
    val description: String? = null,
    val image: String? = null,
)

data class RPolicy(
    val policy: String? = null,
)

data class RUnavailableD(
    val date: String? = null,
    val description: String? = null,
    val startTime: String? = null,
)