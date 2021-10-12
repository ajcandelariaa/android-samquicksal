package com.altwav.samquicksal2.models

data class RestaurantAboutModel(
    val id: Int? = null,
)

data class RestaurantModelResponse(
    val rName: String,
    val rAddress: String,
    val rSchedule: List<RSchedule>,
    val rTableCapacity: Int,
    val rTableStatus: Int,
    val rReservedTables: Int,
    val rNumberOfPeople: Int,
    val rNumberOfQueues: Int,
    val rPosts: List<RPosts>,
)

data class RSchedule(
    val Closing: String,
    val Day: String,
    val Opening: String
)

data class RPosts(
    val description: String,
    val image: String
)
