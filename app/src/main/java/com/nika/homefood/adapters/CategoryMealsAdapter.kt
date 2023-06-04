package com.nika.homefood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.nika.homefood.databinding.CategoryItemBinding
import com.nika.homefood.databinding.MealItemBinding
import com.nika.homefood.pojo.Category
import com.nika.homefood.pojo.MealByCategory

class CategoryMealsAdapter :RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewholder>() {

    private var mealsList= ArrayList<MealByCategory>()
    var onItemClick: ((MealByCategory) -> Unit?)? =null

    fun setMealsList(mealsList: List<MealByCategory>) {
        this.mealsList = ArrayList(mealsList)
        notifyDataSetChanged()
    }


    inner class CategoryMealsViewholder(var binding : MealItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewholder {

        return  CategoryMealsViewholder(
            MealItemBinding.
            inflate(LayoutInflater.from(parent.context))
        )

    }

    override fun getItemCount(): Int {


       return mealsList.size

    }

    override fun onBindViewHolder(holder: CategoryMealsViewholder, position: Int) {


        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text=mealsList[position].strMeal
        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(mealsList[position])
        }

    }
}