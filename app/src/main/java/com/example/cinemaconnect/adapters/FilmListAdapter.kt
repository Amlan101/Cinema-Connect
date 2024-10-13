package com.example.cinemaconnect.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.cinemaconnect.databinding.FilmViewholderBinding
import com.example.cinemaconnect.models.Film
import com.example.cinemaconnect.presentation.activities.FilmDetailActivity
import java.util.ArrayList

class FilmListAdapter(private val filmList: ArrayList<Film>):
    RecyclerView.Adapter<FilmListAdapter.FilmListViewHolder>() {

    private var context: Context? = null

    inner class FilmListViewHolder(private val binding: FilmViewholderBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film){
            binding.titleFilmTxtView.text = film.Title
            val requestOptions = RequestOptions()
                .transform(CenterCrop(), RoundedCorners(30))

            Glide.with(context!!)
                .load(film.Poster)
                .apply (requestOptions)
                .into(binding.picFilmImgView)

            binding.root.setOnClickListener {
                val intent = Intent(context, FilmDetailActivity::class.java)
                intent.putExtra("object", film)
                context!!.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmListViewHolder {
        context = parent.context
        val binding = FilmViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) {
        holder.bind(filmList[position])
    }

    override fun getItemCount(): Int {
        return filmList.size
    }
}