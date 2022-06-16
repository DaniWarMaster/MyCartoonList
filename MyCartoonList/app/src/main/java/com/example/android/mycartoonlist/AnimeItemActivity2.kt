package com.example.android.mycartoonlist

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.databaseHandlers.UserDatabaseHandler
import com.example.android.mycartoonlist.dataclasses.Data
import com.example.android.mycartoonlist.dataclasses.UserAnime
import com.example.android.mycartoonlist.mainList.MainListElementFragment
import com.example.android.mycartoonlist.personalList.PersonalListElementFragment

class AnimeItemActivity2 : AppCompatActivity() {

    private lateinit var animeItem: UserAnime;
    private val userDatabaseHandler = UserDatabaseHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_item)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.drawer_fragment_container, PersonalListElementFragment()).commit()

        if(intent.hasExtra("personalData")) {
            animeItem = intent.getSerializableExtra("personalData") as UserAnime
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_personal_anime_details_action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_bar_personalAnimes_details_save -> {
                /// add anime to database

            }
            R.id.action_bar_personalAnimes_details_remove -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}