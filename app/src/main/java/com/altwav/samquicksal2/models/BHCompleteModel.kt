package com.altwav.samquicksal2.models

data class BHCompleteModel(
    val cust_id: Int? = null,
    val book_id: Int? = null,
    val book_type: String? = null,
)


data class BHCompleteModelResponse(
    val bookDate: String? = null,
    val checkIn: String? = null,
    val checkOut: String? = null,
    val rName: String? = null,
    val rAddress: String? = null,
    val tableNumber: String? = null,
    val numberOfPersons: Int? = null,
    val bookingType: String? = null,

    val orderSetName: String? = null,
    val orderSetPriceTotal: String? = null,
    val orders: List<OrderingBillOrders>? = null,
    val subtotal: String? = null,
    val numberOfPwd: Int? = null,
    val pwdDiscount: String? = null,
    val childrenPercentage: Int? = null,
    val numberOfChildren: Int? = null,
    val childrenDiscount: String? = null,
    val promoDiscount: String? = null,
    val additionalDiscount: String? = null,
    val rewardName: String? = null,
    val rewardDiscount: String? = null,
    val offenseCharge: String? = null,
    val totalPrice: String? = null,
)
