package com.nika.homefood.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nika.homefood.db.MealDataBase
import com.nika.homefood.pojo.*
import com.nika.homefood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDataBase: MealDataBase
):ViewModel() {

    private  var randomMealLiveData=MutableLiveData<Meal>()
    private var popularItemsLiveData=MutableLiveData<List<MealByCategory>>()
    private var categoriesLiveData=MutableLiveData<List<Category>>()
    private var favortiesMealLiveData= mealDataBase.mealDao().getAllMeals()
    private var bottomsheetLiveDAta= MutableLiveData<Meal>()
    private val searchedMealLiveData=MutableLiveData<List<Meal>>()

//    se stocheza random meal-ul
    fun getRanodmMeal(){

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                if (response.body() != null) {

                    val randomMeal = response.body()!!.meals[0]

                    randomMealLiveData.value=randomMeal

                } else {
                    return
                }

            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.d("HOME FRAGMENT ", t.message.toString())
            }


        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealByCategoryList?> {
            override fun onResponse(call: Call<MealByCategoryList?>, response: Response<MealByCategoryList?>) {
                if (response !=null){
                    popularItemsLiveData.value=response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealByCategoryList?>, t: Throwable) {
                Log.d("HOOME FRAGMENT " , t.message.toString())
            }
        })
    }


    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList?> {
            override fun onResponse(call: Call<CategoryList?>, response: Response<CategoryList?>) {
                if(response != null){

                    categoriesLiveData.value=response.body()!!.categories

                }


            }

            override fun onFailure(call: Call<CategoryList?>, t: Throwable) {
                Log.d("HOOME FRAGMENT " , t.message.toString())
            }
        })
    }

    fun getMealByid(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                val meal =response.body()?.meals?.first()
                meal.let {
                    bottomsheetLiveDAta.value=it
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {

                Log.d("HOME VIEW MODEL " ,t.message.toString())

            }
        })
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {

            mealDataBase.mealDao().upsertMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }

    fun observRandomMealLiveData():LiveData<Meal>{

        return randomMealLiveData
    }

    fun observPopularItemsLiveData():LiveData<List<MealByCategory>>{
        return popularItemsLiveData
    }


    fun observCategoiresLiveData() : LiveData<List<Category>>{

        return categoriesLiveData

    }

    fun observeFavoriteMealLiveData():LiveData<List<Meal>>{
        return favortiesMealLiveData
    }

    fun observeBottomSheetMeal():LiveData<Meal>{
     return bottomsheetLiveDAta
    }

    fun searchMeal(searchMeal:String){
        RetrofitInstance.api.searchMeals(searchMeal).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {

                val mealsList=response.body()?.meals
                mealsList?.let {
                    searchedMealLiveData.value=it
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.d("HOME VIEW MODEL " ,t.message.toString())            }
        })
    }

    fun observeSearchedMealLiveData():LiveData<List<Meal>>{
       return searchedMealLiveData
    }
}