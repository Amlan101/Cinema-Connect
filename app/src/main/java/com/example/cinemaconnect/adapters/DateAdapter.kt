package com.example.cinemaconnect.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaconnect.R
import com.example.cinemaconnect.databinding.ItemDateBinding

class DateAdapter(private val dateSlots: List<String>): RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class DateViewHolder(val binding: ItemDateBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(date: String){
                val dateParts = date.split("/")

                if(dateParts.size == 3){
                    binding.dayTxtView.text = dateParts[0]
                    binding.dateMonthTxtView.text = dateParts[1] + " " + dateParts[2]


                if(selectedPosition == position){
                    binding.mailLayout.setBackgroundResource(R.drawable.white_bg)
                    binding.dayTxtView.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                    binding.dateMonthTxtView.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                } else {
                    binding.mailLayout.setBackgroundResource(R.drawable.light_black_background)
                    binding.dayTxtView.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    binding.dateMonthTxtView.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
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

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateAdapter.DateViewHolder {
        return DateViewHolder(ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DateAdapter.DateViewHolder, position: Int) {
        holder.bind(dateSlots[position])
    }

    override fun getItemCount(): Int {
        return dateSlots.size
    }

}