package com.nika.homefood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nika.homefood.databinding.PopularItemsBinding
import com.nika.homefood.fragments.HomeFragment

import com.nika.homefood.pojo.MealByCategory

class MostPopularAdapter(private val context: HomeFragment):RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    lateinit var onItemClick: ((MealByCategory) -> Unit)
     var onLongItemClick:((MealByCategory)->Unit)?=null
     var mealsList = ArrayList<MealByCategory>()

    fun setMeals(  mealsList :ArrayList<MealByCategory>){

        this.mealsList=mealsList
        notifyDataSetChanged()
    }




    class PopularMealViewHolder(val binding : PopularItemsBinding):RecyclerView.ViewHolder(binding.root){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {

        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)


        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnClickListener{
            onLongItemClick?.invoke(mealsList[position])
            true
        }
    }
}