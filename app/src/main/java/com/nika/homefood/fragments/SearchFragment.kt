package com.nika.homefood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nika.homefood.R
import com.nika.homefood.ViewModel.HomeViewModel
import com.nika.homefood.activities.MainActivity
import com.nika.homefood.activities.MealActivity
import com.nika.homefood.adapters.MealsAdapter
import com.nika.homefood.databinding.FragmentSearchBinding
import com.nika.homefood.pojo.Meal
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var searchRecyclerViewAdapter:MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        prepareRecyclerViewAdapter()

        binding.icSearch.setOnClickListener{
            searchMeals()

        }

        observeSearchedMealsLiveData()

        onItemClick()

        var searchJob:Job?=null
        binding.edSearchBox.addTextChangedListener{searchedQuery ->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchedQuery.toString())
            }

        }

    }

    private fun observeSearchedMealsLiveData() {

        viewModel.observeSearchedMealLiveData().observe(viewLifecycleOwner, Observer {

            searchRecyclerViewAdapter.differ.submitList(it)
        })
    }

    private fun searchMeals() {


        val searchQuery=binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()){

            viewModel.searchMeal(searchQuery)

        }

    }

    private fun prepareRecyclerViewAdapter() {
        searchRecyclerViewAdapter= MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=searchRecyclerViewAdapter
        }
    }


    private fun onItemClick() {
        searchRecyclerViewAdapter.onItemClick={



            val intent= Intent(activity, MealActivity::class.java)
            val bundle =Bundle().apply{
                putString(HomeFragment.MEAL_ID, it.idMeal)
                putString(HomeFragment.MEAL_NAME, it.strMeal)
                putString(HomeFragment.MEAL_THUMB, it.strMealThumb)
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }
}