package com.nika.homefood.pojo


import com.google.gson.annotations.SerializedName

data class MealByCategory(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String
)