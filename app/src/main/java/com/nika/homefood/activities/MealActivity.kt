package com.nika.homefood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.nika.homefood.R
import com.nika.homefood.ViewModel.MealViewModel
import com.nika.homefood.ViewModel.MealViewModelFactory
import com.nika.homefood.databinding.ActivityMealBinding
import com.nika.homefood.db.MealDataBase
import com.nika.homefood.fragments.HomeFragment
import com.nika.homefood.pojo.Meal

class MealActivity : AppCompatActivity() {
    lateinit var binding :ActivityMealBinding
    lateinit var mealId:String
    lateinit var mealName:String
    lateinit var mealThumb:String
    lateinit var extras :Intent
    private lateinit var ytLink:String
    private lateinit var mealMvvm: MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMealBinding.inflate(layoutInflater)



        extras=intent

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mealDataBase=MealDataBase.getInstance(this)
        val viewModelFactory=MealViewModelFactory(mealDataBase)
        mealMvvm=ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]


//        mealMvvm=ViewModelProvider(this)[MealViewModel::class.java]





        getMealInfoFromIntent()
        setInformationInView()
        mealMvvm.getMealDetailDetail(mealId)
        observMealDetailsLiveData()

        onYoutoubeCliked()

        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnSave.setOnClickListener {

            mealTosave.let {
                mealMvvm.insertMeal(it!!)
                Toast.makeText(this,"Meal has Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var mealTosave:Meal?=null
    private fun onYoutoubeCliked() {
        binding.imgYt.setOnClickListener{
            val intent =Intent(Intent.ACTION_VIEW, Uri.parse(ytLink))
            startActivity(intent)
        }
    }

    private fun observMealDetailsLiveData() {
        mealMvvm.observMealDetailLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
               val meal=t

                mealTosave=meal
                binding.tvCategoryInfo.text="Category : ${meal!!.strCategory}"
                binding.tvAreaInfo.text="Area : ${meal.strArea}"
                binding.tvInstructionsSteps.text=meal.strInstructions

                ytLink= meal.strYoutube!!
            }
        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {

        if(extras != null){
            mealId= extras.getStringExtra(HomeFragment.MEAL_ID)!!
            mealName=extras.getStringExtra(HomeFragment.MEAL_NAME)!!
            mealThumb=extras.getStringExtra(HomeFragment.MEAL_THUMB)!!

        }

    }
}