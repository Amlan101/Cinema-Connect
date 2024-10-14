package com.example.cinemaconnect.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaconnect.R
import com.example.cinemaconnect.databinding.ItemTimeBinding

class TimeAdapter(private val timeSlots: List<String>): RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class TimeViewHolder(val binding: ItemTimeBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(time: String){
                binding.timeTxtView.text = time
                if(selectedPosition == position){
                    binding.timeTxtView.setBackgroundResource(R.drawable.white_bg)
                    binding.timeTxtView.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                } else {
                    binding.timeTxtView.setBackgroundResource(R.drawable.light_black_background)
                    binding.timeTxtView.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                }

                binding.root.setOnClickListener {
                    val position = position
                    if(position != RecyclerView.NO_POSITION){
                        lastSelectedPosition = selectedPosition
                        selectedPosition = position
                        notifyItemChanged(lastSelectedPosition)
                        notifyItemChanged(selectedPosition)
                    }
                }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeAdapter.TimeViewHolder {
        return TimeViewHolder(ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TimeAdapter.TimeViewHolder, position: Int) {
        holder.bind(timeSlots[position])
    }

    override fun getItemCount(): Int {
        return timeSlots.size
    }

}