package com.example.android.mycartoonlist

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

                    val builder = AlertDialog.Builder(this)
                        .create()

                    val view = layoutInflater.inflate(R.layout.add_dialog_box_layout,null)

                    val buttonDismiss = view.findViewById<Button>(R.id.dialogDismiss_button)

                    val buttonAccept = view.findViewById<Button>(R.id.dialogAccept_button)

                    val score = view.findViewById<Spinner>(R.id.alertDialog_selectScore)
                    ArrayAdapter.createFromResource(
                        view.context,
                        R.array.spinner_score,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        score.adapter = adapter
                    }

                    val status = view.findViewById<Spinner>(R.id.alertDialog_selectCategory)
                    ArrayAdapter.createFromResource(
                        view.context,
                        R.array.spinner_status,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        status.adapter = adapter
                    }

                    builder.setView(view)
                    buttonDismiss.setOnClickListener {
                        builder.dismiss()
                    }

                    buttonAccept.setOnClickListener {
                        val scoreAct = Integer.parseInt(score.selectedItem as String)
                        val statusText : String = status.selectedItem as String
                        Log.d("AnimeItemActivity", "onOptionsItemSelected: ${Common.userData}")
                        userDatabaseHandler.addAnimeToUser(Common.userData!!.id.toString(),
                            UserAnime(animeItem,scoreAct,statusText,""))

                        Toast.makeText(this,"Anime Added",Toast.LENGTH_SHORT).show()
                        builder.dismiss()
                    }

                    builder.setCanceledOnTouchOutside(false)
                    builder.show()

                }
                else {
                    Toast.makeText(this,"User Not Logged",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}