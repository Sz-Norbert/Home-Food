package com.nika.homefood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nika.homefood.R
import com.nika.homefood.ViewModel.HomeViewModel
import com.nika.homefood.activities.CategoryMealsActivity
import com.nika.homefood.activities.MainActivity
import com.nika.homefood.activities.MealActivity
import com.nika.homefood.adapters.CategoriesAdapter
import com.nika.homefood.adapters.MostPopularAdapter
import com.nika.homefood.databinding.FragmentHomeBinding
import com.nika.homefood.fragments.bottomsheet.MealBottomSheetFragment
import com.nika.homefood.pojo.MealByCategory
import com.nika.homefood.pojo.Meal


class HomeFragment : Fragment() {

    private lateinit var randomMeal:Meal

    companion object{
        const val MEAL_ID="package com.nika.homefood.fragments.idMeal"
        const val MEAL_NAME="package com.nika.homefood.fragments.nameMeal"
        const val MEAL_THUMB="package com.nika.homefood.fragments.thumbMeal"
        const val CATEGORT_NAME="package com.nika.homefood.fragments.categoryName"
    }

    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var popularitemsAdapter:MostPopularAdapter
    private lateinit var categoryAdapter :CategoriesAdapter
    private lateinit var mealList : ArrayList<MealByCategory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        popularitemsAdapter= MostPopularAdapter(this@HomeFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentHomeBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRanodmMeal()
        observeRandomMeal()
        preparePopularItemsRecyclerView()
        onRandomMealClicked()

        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()

        onPopularItemLongClick()

    }

    private fun onPopularItemLongClick() {
        popularitemsAdapter.onLongItemClick={
            val mealBottomSheetFragment=MealBottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "MEAL INFO")
        }
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = { category ->
            val intent =Intent(activity, CategoryMealsActivity::class.java)
            val bundle =Bundle().apply {
                putString(CATEGORT_NAME , category.strCategory)
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }

        onSearchIconClick()
    }

    private fun onSearchIconClick() {

        binding.imgSearch.setOnClickListener{

            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)

        }

    }


    private fun prepareCategoriesRecyclerView() {

        categoryAdapter= CategoriesAdapter()
        binding.recyclerViewCategory.apply {
            layoutManager=GridLayoutManager(context, 3 , GridLayoutManager.VERTICAL , false)
            adapter=categoryAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observCategoiresLiveData().observe(viewLifecycleOwner
        ) {categories->
            categoryAdapter.setCategoryList(categories )


        }
    }

    private fun onPopularItemClick() {
        popularitemsAdapter.onItemClick={meal->
            val intent=Intent(activity, MealActivity::class.java)
            val bundle =Bundle().apply{
                putString(MEAL_ID , meal.idMeal)
                putString(MEAL_NAME, meal.strMeal)
                putString(MEAL_THUMB, meal.strMealThumb)
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL , false)
            adapter=popularitemsAdapter

        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observPopularItemsLiveData().observe(viewLifecycleOwner) { mealList ->

            popularitemsAdapter.setMeals(mealList as ArrayList<MealByCategory>)



        }


    }

    private fun onRandomMealClicked() {
        binding.randomMeal.setOnClickListener{


            val intent=Intent(activity, MealActivity::class.java)
            val bundle =Bundle().apply{
                putString(MEAL_ID , randomMeal.idMeal)
                putString(MEAL_NAME, randomMeal.strMeal)
                putString(MEAL_THUMB, randomMeal.strMealThumb)
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }

    private fun observeRandomMeal() {
       viewModel.observRandomMealLiveData().observe(viewLifecycleOwner
       ) { t ->
           Glide.with(this@HomeFragment)
               .load(t!!.strMealThumb)
               .into(binding.imgRandomMeal)

           this.randomMeal=t
       }
    }
}