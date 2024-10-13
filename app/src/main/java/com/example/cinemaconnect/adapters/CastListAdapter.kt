package com.example.cinemaconnect.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemaconnect.databinding.CastViewholderBinding
import com.example.cinemaconnect.models.Cast

class CastListAdapter(private val castList: ArrayList<Cast>): RecyclerView.Adapter<CastListAdapter.CastListViewHolder>() {

    private var context: Context? = null

    inner class CastListViewHolder(private val binding: CastViewholderBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(cast: Cast){
                context?.let{
                    Glide.with(it)
                        .load(cast.PicUrl)
                        .into(binding.actorPicImgView)
                }
                binding.nameCastTxtView.text = cast.Actor
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastListAdapter.CastListViewHolder {
        context = parent.context
        val binding = CastViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastListAdapter.CastListViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    override fun getItemCount(): Int {
        return castList.size
    }
}