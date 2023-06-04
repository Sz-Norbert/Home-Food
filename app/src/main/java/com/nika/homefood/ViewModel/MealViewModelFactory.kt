package com.nika.homefood.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nika.homefood.db.MealDataBase

class MealViewModelFactory(
    val mealDataBase: MealDataBase
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {


        return MealViewModel(mealDataBase) as T

   }
}
