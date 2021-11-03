package com.altwav.samquicksal2.models

data class OrderingBillModel(
    val orderSetName: String? = null,
    val numberOfPersons: Int? = null,
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

data class OrderingBillOrders(
    val foodItemName: String? = null,
    val quantity: Int? = null,
    val price: String? = null,
)
