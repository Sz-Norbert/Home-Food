package com.nika.homefood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nika.homefood.R
import com.nika.homefood.ViewModel.CategoryMealsViewModel
import com.nika.homefood.adapters.CategoryMealsAdapter
import com.nika.homefood.databinding.ActivityCategoryMealsBinding
import com.nika.homefood.databinding.ActivityMealBinding
import com.nika.homefood.databinding.CategoryItemBinding
import com.nika.homefood.fragments.HomeFragment

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel
    lateinit var extras : Intent
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        extras=intent

        categoryMealsViewModel=ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        if (extras!=null) {
            categoryMealsViewModel.getMealsByCategory(extras.getStringExtra(HomeFragment.CATEGORT_NAME)!!)
        }else return
        categoryMealsViewModel.observMealsLiveData().observe(this, Observer {mealsList->

            binding.tvCategoryCount.text=mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)

        })

        onMealClicked()
    }

    private fun onMealClicked() {
        categoryMealsAdapter.onItemClick= { meal ->
            val intent = Intent(this@CategoryMealsActivity, MealActivity::class.java)
            val bundle =Bundle().apply {
                putString(HomeFragment.MEAL_ID, meal.idMeal)
                putString(HomeFragment.MEAL_NAME,meal.strMeal)
                putString(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            }
            intent.putExtras(bundle)
            startActivity(intent)
            }
    }

    private fun prepareRecyclerView() {

        categoryMealsAdapter= CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }


}