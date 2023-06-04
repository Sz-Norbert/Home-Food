package com.nika.homefood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nika.homefood.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun upsertMeal(meal: Meal)


     @Delete
     suspend fun delete(meal: Meal)

     @Query("SELECT * FROM mealInformation")
     fun getAllMeals():LiveData<List<Meal>>

}