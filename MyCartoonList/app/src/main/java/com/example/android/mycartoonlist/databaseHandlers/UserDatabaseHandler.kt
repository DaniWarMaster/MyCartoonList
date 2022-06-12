package com.example.android.mycartoonlist.databaseHandlers

import android.util.Log
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.dataclasses.User
import com.example.android.mycartoonlist.dataclasses.UserAnime
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserDatabaseHandler {
    private val database = FirebaseDatabase.getInstance(Common.firebaseDatabasLocation).getReference("users")

    fun addUser(user: User) {
        val id = database.push().key
        Log.d("UserDatabaseHandler", "addUser: -----> ${id}")

        val user = User(id, user.username, user.email, user.animesNumber, user.personalAnimes)

        Log.d("UserDatabaseHandler", "addUser: -----> ${id}")
        database.child(id.toString()).setValue(user)
    }

    fun addAnimeToUser(id: String, userAnime: UserAnime) {
        val animeId = Common.userData!!.animesNumber
        Log.d("UserDatabaseHandler", "addUser: userID -----> ${id}")
        Log.d("UserDatabaseHandler", "addUser: animeID -----> ${animeId}")
        database.child(id).child("personalAnimes").child(animeId.toString()).setValue(userAnime)
        Common.userData!!.animesNumber = Common.userData!!.animesNumber + 1
        database.child(id).child("animesNumber").setValue(Common.userData!!.animesNumber)
    }

    fun getUserByEmail(email: String) : User? {
        var user : User? = null
        database.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    Log.d("UserDatabaseHandler", "getUserByEmail: onDataChanged: Success -- ${snapshot.getValue()}")
                    snapshot.children.forEach { child ->
                        user = child.getValue(User::class.java)!!
                        Common.userData = user
                    }
                    Log.d("UserDatabaseHandler", "getUserByEmail: onDataChanged: Success -- ${user}")
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.d("UserDatabaseHandler", "getUserByEmail: onCancelled: ERROR -- couldn't retrieve user")
            }

        })
        Log.d("UserDatabaseHandler", "getUserByEmail: ${user}")

        return user
    }
}