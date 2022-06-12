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

class AnimeItemActivity : AppCompatActivity() {

    private lateinit var animeItem: Data;
    private val userDatabaseHandler = UserDatabaseHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_item)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.drawer_fragment_container, MainListElementFragment()).commit()

        if(intent.hasExtra("data")) {
            animeItem = intent.getSerializableExtra("data") as Data
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_anime_details_action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_bar_animes_details_add -> {
                /// add anime to database
                if(Common.isLogged) {
                    Log.d("AnimeItemActivity", "onOptionsItemSelected: ${Common.userData}")
                    userDatabaseHandler.addAnimeToUser(Common.userData!!.id.toString(),
                        UserAnime(animeItem,9,"WATCHED",""))

                    Toast.makeText(this,"Anime Added",Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this,"User Not Logged",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}