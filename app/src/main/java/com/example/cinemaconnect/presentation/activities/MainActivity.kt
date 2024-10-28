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
    import com.example.cinemaconnect.presentation.fragments.CartFragment
    import com.example.cinemaconnect.presentation.fragments.ExplorerFragment
    import com.example.cinemaconnect.presentation.fragments.FavoritesFragment
    import com.example.cinemaconnect.presentation.fragments.ProfileFragment
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

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            database = FirebaseDatabase.getInstance()

            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

            // Set default fragment to ExplorerFragment
            if (savedInstanceState == null) {
                binding.bottomNavBar.setItemSelected(R.id.nav_explorer, true)  // Set default selected item
                loadFragment(ExplorerFragment())
            }

            // Set up ChipNavigationBar item selection handling
            binding.bottomNavBar.setOnItemSelectedListener { menuItemId ->
                when (menuItemId) {
                    R.id.nav_explorer -> loadFragment(ExplorerFragment())
                    R.id.nav_favourite -> loadFragment(FavoritesFragment())
                    R.id.nav_cart -> loadFragment(CartFragment())
                    R.id.nav_profile -> loadFragment(ProfileFragment())
                }
            }
        }

        private fun loadFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }