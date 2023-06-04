package com.nika.homefood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nika.homefood.databinding.CategoryItemBinding
import com.nika.homefood.pojo.Category
import com.nika.homefood.pojo.CategoryList

class CategoriesAdapter:RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    var categoryList =ArrayList<Category>()
    var onItemClick: ((Category) -> Unit?)? =null

    fun setCategoryList(categoryList:List<Category>){
        this.categoryList=categoryList as ArrayList
        notifyDataSetChanged()
    }

    inner class  CategoryViewHolder(val binding :CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        return CategoryViewHolder(CategoryItemBinding
            .inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {

        return categoryList.size

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text=categoryList[position].strCategory
        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(categoryList[position])
        }
    }


}