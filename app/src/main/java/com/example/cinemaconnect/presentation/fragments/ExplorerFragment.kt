package com.example.cinemaconnect.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.cinemaconnect.R
import com.example.cinemaconnect.adapters.FilmListAdapter
import com.example.cinemaconnect.adapters.SliderAdapter
import com.example.cinemaconnect.databinding.FragmentExplorerBinding
import com.example.cinemaconnect.models.Film
import com.example.cinemaconnect.models.SliderItems
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.abs

class ExplorerFragment : Fragment() {

    private var _binding: FragmentExplorerBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase

    private val sliderHandler = Handler()
    private val sliderRunnable = Runnable {
        binding.sliderViewPager2.currentItem += 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExplorerBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()

        initBanner()
        initTopMovies()
        initUpcommingMovies()

        return binding.root
    }

    private fun initTopMovies() {
        val myRef: DatabaseReference = database.getReference("Items")
        binding.progressBarTopMovies.visibility = View.VISIBLE
        val films = ArrayList<Film>()

        myRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(issue in snapshot.children){
                        val film = issue.getValue(Film::class.java)
                        film?.firebaseId = issue.key ?: ""
                        if (film != null) {
                            films.add(film)
                        }
                        //                            films.add(issue.getValue(Film::class.java)!!)
                    }
                    if(films.isNotEmpty()){
                        binding.topMoviesRecyclerView.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.topMoviesRecyclerView.adapter = FilmListAdapter(films)
                    }
                    binding.progressBarTopMovies.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Error", error.message)
            }

        })
    }

    private fun initUpcommingMovies() {
        val myRef: DatabaseReference = database.getReference("Upcomming")
        binding.upcomingMoviesProgressBar.visibility = View.VISIBLE
        val films = ArrayList<Film>()

        myRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(issue in snapshot.children){
                        films.add(issue.getValue(Film::class.java)!!)
                    }
                    if(films.isNotEmpty()){
                        binding.upcomingMoviesRecyclerView.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.upcomingMoviesRecyclerView.adapter = FilmListAdapter(films)
                    }
                    binding.upcomingMoviesProgressBar.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Error", error.message)
            }

        })
    }

    private fun initBanner() {
        val myRef: DatabaseReference = database.getReference("Banners")
        binding.progressBarSlider.visibility = View.VISIBLE

        myRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderItems>()
                for(childSnapshot in snapshot.children){
                    val list = childSnapshot.getValue(SliderItems::class.java)
                    if(list != null) lists.add(list)
                }
                binding.progressBarSlider.visibility = View.GONE
                banners(lists)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Error", error.message)
            }

        })
    }

    private fun banners(lists: MutableList<SliderItems>) {
        binding.sliderViewPager2.adapter = SliderAdapter(lists, binding.sliderViewPager2)
        binding.sliderViewPager2.clipToPadding = false
        binding.sliderViewPager2.clipChildren = false
        binding.sliderViewPager2.offscreenPageLimit = 3
        binding.sliderViewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }
        binding.sliderViewPager2.setPageTransformer(compositePageTransformer)
        binding.sliderViewPager2.currentItem = 1
        binding.sliderViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 2000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}