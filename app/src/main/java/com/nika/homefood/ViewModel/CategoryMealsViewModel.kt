package com.nika.homefood.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nika.homefood.pojo.MealByCategory
import com.nika.homefood.pojo.MealByCategoryList
import com.nika.homefood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {

    val mealsLiveData= MutableLiveData<List<MealByCategory>>()

    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealByCategoryList?> {
            override fun onResponse(
                call: Call<MealByCategoryList?>,
                response: Response<MealByCategoryList?>
            ) {
              response.body()?.let {mealList->
                  mealsLiveData.value=mealList.meals

              }
            }

            override fun onFailure(call: Call<MealByCategoryList?>, t: Throwable) {

            Log.d("CATEGORY MEAL VIEW MODEL" , t.message.toString())

            }
        })
    }

    fun observMealsLiveData():LiveData<List<MealByCategory>>{
        return mealsLiveData
    }
}