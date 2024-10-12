package com.example.cinemaconnect.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.cinemaconnect.databinding.SliderViewholderBinding
import com.example.cinemaconnect.models.SliderItems

class SliderAdapter(
    private var sliderItems: MutableList<SliderItems>,
    private val viewPager2: ViewPager2
): RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private var context: Context? = null
    private val runnable = Runnable{
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        context = parent.context
        val binding = SliderViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    inner class SliderViewHolder(private val binding: SliderViewholderBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(sliderItem: SliderItems){
                val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(60))
                context?.let {
                    Glide.with(it)
                        .load(sliderItem.image)
                        .apply (requestOptions)
                        .into(binding.imageSlider)
                }
            }
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(sliderItems[position])
        if(position == sliderItems.size-2){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

}