package com.example.android.mycartoonlist.databaseHandlers

import android.util.Log
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.dataclasses.Data
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AnimesDatabaseHandler {
    private val databse = FirebaseDatabase.getInstance(Common.firebaseDatabasLocation).getReference("data")

    fun getAllAnimes() {
        databse.limitToFirst(500).addValueEventListener(postListener)
    }

    val postListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if(Common.animesList == null) {
                Common.animesList = ArrayList<Data>()
            }

            Common.animesList.clear()
            if(snapshot.exists()) {
                snapshot.children.forEach { child ->
                    println("-----> Item Found: ${child}")

                    val item : Data? = child.getValue(Data::class.java)
                    println("-----> Item Converted: ${item}")
                    if(item != null) {
                        Common.animesList.add(item)
                        println("-----> Item Inserted")
                    }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Error", "Error at receiving the database")
        }
    }
}