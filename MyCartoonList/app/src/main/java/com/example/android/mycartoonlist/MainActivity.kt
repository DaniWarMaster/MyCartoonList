package com.example.android.mycartoonlist

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.databaseHandlers.UserDatabaseHandler
import com.example.android.mycartoonlist.mainList.MainListFragment
import com.example.android.mycartoonlist.newsFeed.NewsFeedFragment
import com.example.android.mycartoonlist.profile.ProfileFragment
import com.example.android.mycartoonlist.profile.ProfileNotLoggedFragment
import com.example.android.mycartoonlist.settings.SettingsFragment
import com.google.android.material.navigation.NavigationView
import com.example.android.mycartoonlist.personalList.PersonalListFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var auth: FirebaseAuth
    private lateinit var navigationView: NavigationView
    private val userDatabaseHandler = UserDatabaseHandler()

    public override fun onStart() {
        super.onStart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // intialize firebase
        auth = Firebase.auth

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload();
            Log.d("MainActivity: ", "User Already Logged ${auth.currentUser}")
            Common.isLogged = true
            Common.user = currentUser
            Common.userData = userDatabaseHandler.getUserByEmail(currentUser.email.toString())
        }
        else {
            Log.d("MainActivity: ", "User Not Logged At App Start")
            Common.isLogged = false
        }

        // get toolbar from main activity layout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // get the drawer from main activity layout
        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.navigation_view)
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
        /*
        if (Common.isLogged) {
            navigationView.menu.findItem(R.id.nav_login).isVisible = false
            navigationView.menu.findItem(R.id.nav_logout).isVisible = true
        }
        else {
            navigationView.menu.findItem(R.id.nav_login).isVisible = true
            navigationView.menu.findItem(R.id.nav_logout).isVisible = false
        }
        */
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
        else {
            AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes"
                ) { _, _ -> super@MainActivity.onBackPressed() }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private val intentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, MainListFragment()).commit()
                //navigationView.menu.findItem(R.id.nav_login).isVisible = false
                //navigationView.menu.findItem(R.id.nav_logout).isVisible = true
                navigationView.setCheckedItem(R.id.nav_mainList)
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
                //supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, LoginFragment()).commit()
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra(Common.LoginActivityIntent, true)
                }
                intentLauncher.launch(intent)
            }
            R.id.nav_profile -> {
                if (Common.isLogged) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.drawer_fragment_container, ProfileFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit()
                }
                else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.drawer_fragment_container, ProfileNotLoggedFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit()
                }
            }
            R.id.nav_personalList -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.drawer_fragment_container, PersonalListFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.drawer_fragment_container, SettingsFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show()
                //// logout operation
                Common.isLogged = false
                auth.signOut()
                //navigationView.menu.findItem(R.id.nav_logout).isVisible = false
                //navigationView.menu.findItem(R.id.nav_login).isVisible = true
                println("----> Login Button visisble: ${navigationView.menu.findItem(R.id.nav_login).isVisible}")
                //// redirecting to news feed fragment
                supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, NewsFeedFragment()).commit()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}