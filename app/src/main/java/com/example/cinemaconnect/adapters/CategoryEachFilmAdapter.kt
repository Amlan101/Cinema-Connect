package com.example.cinemaconnect.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaconnect.databinding.CategoryViewholderBinding

class CategoryEachFilmAdapter(private val list: List<String>):
    RecyclerView.Adapter<CategoryEachFilmAdapter.CategoryViewHolder>() {

        inner class CategoryViewHolder(val binding: CategoryViewholderBinding):
        RecyclerView.ViewHolder(binding.root) 

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryEachFilmAdapter.CategoryViewHolder {
        val binding = CategoryViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryEachFilmAdapter.CategoryViewHolder,
        position: Int
    ) {
        holder.binding.titleCategoryTxtView.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}