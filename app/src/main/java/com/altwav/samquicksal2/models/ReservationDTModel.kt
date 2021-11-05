package com.altwav.samquicksal2.models

data class ReservationDTModel(
    val modDaysBFRAD: List<String>? = null,
    val modDatesBFRAD: List<String>? = null,
    val storeTrueDates: List<String>? = null,
    val modTimeBFRAT: List<List<String>>? = null,
)