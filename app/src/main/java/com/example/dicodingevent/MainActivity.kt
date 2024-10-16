// MainActivity.kt
package com.example.dicodingevent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dicodingevent.databinding.ActivityMainBinding
import com.example.dicodingevent.ui.SearchEventFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_upcoming,
                R.id.navigation_finished,
                R.id.navigation_favorite_event,
                R.id.navigation_home,
                R.id.navigation_setting
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_search) as NavHostFragment
                val navController = navHostFragment.navController
                findViewById<FragmentContainerView>(R.id.nav_host_fragment_search).visibility =
                    View.VISIBLE
                findViewById<FragmentContainerView>(R.id.nav_host_fragment_activity_main).visibility =
                    View.GONE
                navController.navigate(R.id.searchEventFragment)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_search) as NavHostFragment
                val navController = navHostFragment.navController
                navController.popBackStack()
                findViewById<FragmentContainerView>(R.id.nav_host_fragment_search).visibility =
                    View.GONE
                findViewById<FragmentContainerView>(R.id.nav_host_fragment_activity_main).visibility =
                    View.VISIBLE
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_search) as NavHostFragment
                val searchFragment =
                    navHostFragment.childFragmentManager.fragments.firstOrNull() as? SearchEventFragment
                searchFragment?.searchEvents(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return true
    }
}