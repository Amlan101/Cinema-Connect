package com.example.cinemaconnect.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaconnect.R
import com.example.cinemaconnect.databinding.SeatItemBinding
import com.example.cinemaconnect.models.Seat

class SeatListAdapter(
    private val seatList: List<Seat>,
    private val context: Context,
    private val selectedSeat: SelectedSeat
) : RecyclerView.Adapter<SeatListAdapter.SeatListViewHolder>() {

    private val selectedSeatName = ArrayList<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeatListViewHolder {
        return SeatListViewHolder(
            SeatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    class SeatListViewHolder(val binding: SeatItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onBindViewHolder(holder: SeatListViewHolder, position: Int) {
        val seat = seatList[position]
        holder.binding.seat.text = seat.name

        when(seat.status){
            Seat.SeatStatus.AVAILABLE -> {
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_available)
                holder.binding.seat.setTextColor(context.getColor(R.color.white))
            }
            Seat.SeatStatus.UNAVAILABLE -> {
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_unavailable)
                holder.binding.seat.setTextColor(context.getColor(R.color.grey))
            }
            Seat.SeatStatus.SELECTED -> {
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_selected)
                holder.binding.seat.setTextColor(context.getColor(R.color.black))
            }
        }
        holder.binding.seat.setOnClickListener {
            when(seat.status){
                Seat.SeatStatus.AVAILABLE -> {
                    seat.status = Seat.SeatStatus.SELECTED
                    selectedSeatName.add(seat.name)
                    notifyDataSetChanged()
                }
                Seat.SeatStatus.SELECTED -> {
                    seat.status = Seat.SeatStatus.AVAILABLE
                    selectedSeatName.remove(seat.name)
                    notifyDataSetChanged()
                }
                else -> {}
            }
            val selected = selectedSeatName.joinToString(",")
            selectedSeat.Return(selected, selectedSeatName.size)
        }
    }

    override fun getItemCount(): Int {
        return seatList.size
    }

    interface SelectedSeat {
        fun Return(selectedName: String, num: Int)
    }

}