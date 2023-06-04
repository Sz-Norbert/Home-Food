package com.nika.homefood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.nika.homefood.ARG_PARAM1
import com.nika.homefood.ARG_PARAM2
import com.nika.homefood.R
import com.nika.homefood.ViewModel.HomeViewModel
import com.nika.homefood.activities.CategoryMealsActivity
import com.nika.homefood.activities.MainActivity
import com.nika.homefood.activities.MealActivity
import com.nika.homefood.adapters.CategoriesAdapter
import com.nika.homefood.adapters.CategoryMealsAdapter
import com.nika.homefood.databinding.FragmentCategoriesBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private lateinit var binding:FragmentCategoriesBinding
private lateinit var categoryAdapter:CategoriesAdapter
private lateinit var viewModel: HomeViewModel
class CategoriesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoriesBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()
        onCategoryClick()
//        onMealClicked()
    }

    private fun observeCategories() {
        viewModel.observCategoiresLiveData().observe(viewLifecycleOwner, Observer {
            categoryAdapter.setCategoryList(it)
        })
    }

    private fun prepareRecyclerView() {
        categoryAdapter=CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager=GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter= categoryAdapter
        }
    }
    private fun onCategoryClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            val bundle =Bundle().apply {
                putString(HomeFragment.CATEGORT_NAME, category.strCategory)
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }
//    }private fun onMealClicked() {
//       categoryAdapter.onItemClick= { meal ->
//            val intent = Intent(activity, MealActivity::class.java)
//            val bundle =Bundle().apply {
//                putString(HomeFragment.MEAL_ID, meal.)
//               putString(HomeFragment.MEAL_NAME,meal.strMeal)
//                putString(HomeFragment.MEAL_THUMB, meal.strMealThumb) }
//            intent.putExtras(bundle)
//            startActivity(intent)
//        }
//    }

}
    }