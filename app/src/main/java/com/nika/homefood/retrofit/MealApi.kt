package com.nika.homefood.retrofit

import android.icu.text.StringSearch
import com.nika.homefood.pojo.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String) :Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") category: String) :Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category: String):Call<MealByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") search: String):Call<MealList>
}