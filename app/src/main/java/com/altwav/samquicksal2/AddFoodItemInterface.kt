package com.altwav.samquicksal2

import com.altwav.samquicksal2.models.AddFooditemModel

interface AddFoodItemInterface {
//    fun addItem(cust_id: Int, foodItemId: Int, foodName: String, foodPrice: String)
    fun addItem(foodItem: AddFooditemModel)
}