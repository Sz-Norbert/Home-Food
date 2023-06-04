package com.nika.homefood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nika.homefood.ViewModel.HomeViewModel
import com.nika.homefood.activities.MainActivity
import com.nika.homefood.activities.MealActivity
import com.nika.homefood.adapters.MealsAdapter
import com.nika.homefood.databinding.FragmentFavortiesBinding


class FavortiesFragment : Fragment() {
    private lateinit var viewModel:HomeViewModel
    private lateinit var binding :FragmentFavortiesBinding
    private lateinit var favoritesMealAdapter:MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavortiesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observFavorites()
        onItemClick()


        val itemTouchHelper=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean =true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {


                val position = viewHolder.adapterPosition
                val mealList = favoritesMealAdapter.differ.currentList

                if (mealList.isNotEmpty() && position < mealList.size) {
                    val deletedMeal = mealList[position]
                    viewModel.deleteMeal(deletedMeal)

                    Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction(
                        "Undo"
                    ) {
                        viewModel.insertMeal(deletedMeal)
                    }.show()
                }
            }

        }


        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)

    }

    private fun prepareRecyclerView() {

        favoritesMealAdapter= MealsAdapter()
        binding.favRecView.apply {
            layoutManager=GridLayoutManager(context,2, GridLayoutManager.VERTICAL , false)
            adapter=favoritesMealAdapter
        }
    }

    //    !!!!! Nem igy kel REQUIRE ACTIVITY
    private fun observFavorites() {
        viewModel.observeFavoriteMealLiveData().observe(requireActivity(), Observer {meals->
            favoritesMealAdapter.differ.submitList(meals)
        })
    }


    private fun onItemClick() {
        favoritesMealAdapter.onItemClick={



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