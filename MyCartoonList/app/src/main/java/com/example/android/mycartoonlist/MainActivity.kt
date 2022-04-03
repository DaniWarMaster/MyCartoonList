package com.example.android.mycartoonlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.login.LoginFragment
import com.example.android.mycartoonlist.mainList.MainListFragment
import com.example.android.mycartoonlist.newsFeed.NewsFeedFragment
import com.example.android.mycartoonlist.profile.ProfileFragment
import com.example.android.mycartoonlist.profile.ProfileNotLoggedFragment
import com.example.android.mycartoonlist.settings.SettingsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get toolbar from main activity layout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // get the drawer from main activity layout
        drawer = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        // create custom toggle action/button for drawer menu
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        // initialize activity fragment with news_feed as begining only if the fragment is created
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.drawer_fragment_container, NewsFeedFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_newsFeed)
        }
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

    //// trebuie schimbat gestiunea fragmentelor si eventual crearea de noi activitati
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_newsFeed -> {
                supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, NewsFeedFragment()).commit()
            }
            R.id.nav_mainList -> {
                supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, MainListFragment()).commit()
            }
            R.id.nav_login -> {
                supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, LoginFragment()).commit()
            }
            R.id.nav_profile -> {
                if (Common.isLogged) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.drawer_fragment_container, ProfileFragment()).commit()
                }
                else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.drawer_fragment_container, ProfileNotLoggedFragment()).commit()
                }
            }
            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, SettingsFragment()).commit()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show()
                //// logout operation
                Common.isLogged = false
                //// redirecting to news feed fragment
                supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, NewsFeedFragment()).commit()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}