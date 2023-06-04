package com.nika.homefood.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nika.homefood.db.MealDataBase
import com.nika.homefood.pojo.Meal
import com.nika.homefood.pojo.MealList
import com.nika.homefood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    private val mealDataBase: MealDataBase
):ViewModel() {
    private var mealDetailsLiveData=MutableLiveData<Meal>()

    fun  getMealDetailDetail(id:String) {

        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                if(response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {

                Log.d("MEAL ACTIVITY" , t.message.toString())

            }
        })
    }

    fun observMealDetailLiveData():LiveData<Meal>{

        return mealDetailsLiveData
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {

            mealDataBase.mealDao().upsertMeal(meal)
        }
    }



}