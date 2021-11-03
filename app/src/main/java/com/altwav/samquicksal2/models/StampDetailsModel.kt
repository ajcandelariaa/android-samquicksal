package com.altwav.samquicksal2.models

data class StampDetailsModel(
    val stampReward: String? = null,
    val stampValidity: String? = null,
    val stampTasks: List<String>? = null,
    val stampDoneTasks: List<StampDoneTasks>? = null,
)

data class StampDoneTasks(
    val taskName: String? = null,
    val taskDate: String? = null,
)