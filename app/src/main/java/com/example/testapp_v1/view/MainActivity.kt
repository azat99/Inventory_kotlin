package com.example.testapp_v1.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testapp_v1.R
import com.example.testapp_v1.view.main.HomeFragmentDirections
import com.example.testapp_v1.viewmodel.DetailsViewModel
import com.example.testapp_v1.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val detailsViewModel:DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.footer_logo)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_oc_data,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.selectFilialFragment -> {
                    navView.visibility = View.GONE
                    toolbar.visibility = View.GONE
                }
                R.id.detailsFragment -> {
                    supportActionBar?.setIcon(null)
                    navView.visibility = View.GONE
                }
                R.id.archiveFragment -> {
                    supportActionBar?.setIcon(null)
                    navView.visibility = View.GONE
                }
                R.id.connectFragment -> {
                    supportActionBar?.setIcon(null)
                    navView.visibility = View.GONE
                }
                else -> {
                    supportActionBar?.setIcon(R.drawable.footer_logo)
                    navView.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navigated = NavigationUI.onNavDestinationSelected(item!!, navController)
        when (item.itemId) {
            R.id.selectFilialFragment -> {
                this.getSharedPreferences("filial_table", Context.MODE_PRIVATE).edit().clear().apply()
            }
            R.id.deleteDocument -> {
                homeViewModel.deleteAllDataFromDatabase()
            }
            R.id.deleteDetails -> {
                detailsViewModel.deleteAllDataFromDetails()
            }
            R.id.toArchiv -> {
                val action = HomeFragmentDirections.actionNavigationHomeToArchiveFragment()
                findNavController(item.itemId).navigate(action)
            }
        }
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this,R.id.nav_host_fragment),
            null
        )
    }



}
