package com.nika.homefood.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nika.homefood.R
import com.nika.homefood.ViewModel.HomeViewModel
import com.nika.homefood.activities.MainActivity
import com.nika.homefood.activities.MealActivity
import com.nika.homefood.databinding.FragmentMealBottomSheetBinding
import com.nika.homefood.fragments.HomeFragment


private const val MEAL_ID = "param1"


class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMealBottomSheetBinding
    private var mealId: String? = null
    private lateinit var viewModel :HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

            viewModel=(activity as MainActivity).viewModel


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMealBottomSheetBinding.inflate(inflater)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { viewModel.getMealByid(it) }
        observeBottomMealSheet()

        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener{

          if(mealName !=null && mealThumb!=null){
              val intent =Intent(activity,MealActivity::class.java)
              val bundle=Bundle()
              bundle.apply {
                  putString(HomeFragment.MEAL_ID, mealId)
                  putString(HomeFragment.MEAL_NAME, mealName)
                  putString(HomeFragment.MEAL_THUMB, mealThumb)

              }
              intent.putExtras(bundle)
              startActivity(intent)


          }
        }
    }

    private var mealName:String?=null
    private var mealThumb:String?=null
    private fun observeBottomMealSheet() {

        viewModel.observeBottomSheetMeal().observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.imgBottomSheet)

            binding.tvMealCountry.text=it.strArea
            binding.tvMealCategory.text=it.strCategory
            binding.tvMealNameInBtmsheet.text=it.strMeal

            mealName=it.strMeal
            mealThumb=it.strMealThumb
        })
    }

    companion object {

        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}