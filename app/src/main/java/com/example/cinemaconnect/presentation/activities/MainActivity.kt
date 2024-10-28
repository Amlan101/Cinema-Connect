    package com.example.cinemaconnect.presentation.activities

    import android.os.Bundle
    import android.os.Handler
    import android.util.Log
    import android.view.View
    import android.view.WindowManager
    import androidx.appcompat.app.AppCompatActivity
    import androidx.fragment.app.Fragment
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import androidx.viewpager2.widget.CompositePageTransformer
    import androidx.viewpager2.widget.MarginPageTransformer
    import androidx.viewpager2.widget.ViewPager2
    import com.example.cinemaconnect.R
    import com.example.cinemaconnect.adapters.FilmListAdapter
    import com.example.cinemaconnect.adapters.SliderAdapter
    import com.example.cinemaconnect.databinding.ActivityMainBinding
    import com.example.cinemaconnect.models.Film
    import com.example.cinemaconnect.models.SliderItems
    import com.example.cinemaconnect.presentation.fragments.FavoritesFragment
    import com.google.firebase.database.DataSnapshot
    import com.google.firebase.database.DatabaseError
    import com.google.firebase.database.DatabaseReference
    import com.google.firebase.database.FirebaseDatabase
    import com.google.firebase.database.ValueEventListener
    import com.ismaeldivita.chipnavigation.ChipNavigationBar
    import kotlin.math.abs

    class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding
        private lateinit var database: FirebaseDatabase

        private val sliderHandler = Handler()
        private val sliderRunnable = Runnable {
            binding.sliderViewPager2.currentItem += 1
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            database = FirebaseDatabase.getInstance()

            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

            initBanner()
            initTopMovies()
            initUpcommingMovies()

//            val chipNavigationBar = findViewById<ChipNavigationBar>(R.id.bottomNavBar)
//
//            // Set default fragment to ExplorerFragment (home screen)
//            if (savedInstanceState == null) {
//                chipNavigationBar.setItemSelected(R.id.nav_explorer, true)  // Set default selected item
//                loadFragment(ExplorerFragment())
//            }
//
//            // Set up ChipNavigationBar item selection handling
//            chipNavigationBar.setOnItemSelectedListener { menuItemId ->
//                when (menuItemId) {
//                    R.id.nav_explorer -> loadFragment(ExplorerFragment())  // Load home/explorer fragment
//                    R.id.nav_favourite -> loadFragment(FavoritesFragment())  // Load favorites fragment
//                    R.id.nav_cart -> loadFragment(CartFragment())  // Load cart fragment
//                    R.id.nav_profile -> loadFragment(ProfileFragment())  // Load profile fragment
//                }
//            }

        }

//        private fun loadFragment(fragment: Fragment) {
//
//        }

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
                                    this@MainActivity,
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
                                this@MainActivity,
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

    }