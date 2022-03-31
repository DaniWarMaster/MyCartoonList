package com.example.android.mycartoonlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.mycartoonlist.login.LoginFragment
import com.example.android.mycartoonlist.mainList.MainListFragment
import com.example.android.mycartoonlist.newsFeed.NewsFeedFragment
import com.example.android.mycartoonlist.profile.ProfileFragment
import com.example.android.mycartoonlist.settings.SettingsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

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
                supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, ProfileFragment()).commit()
            }
            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, SettingsFragment()).commit()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}