package com.nika.homefood.pojo


import com.google.gson.annotations.SerializedName

data class MealByCategoryList(
    @SerializedName("meals")
    val meals: List<MealByCategory>
)