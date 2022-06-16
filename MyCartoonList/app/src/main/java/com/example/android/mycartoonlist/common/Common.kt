package com.example.android.mycartoonlist.common

import com.example.android.mycartoonlist.dataclasses.Data
import com.example.android.mycartoonlist.dataclasses.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object Common {
    var isLogged = false
    var user : FirebaseUser? = null
    var userData : User? = null
    var animesList : ArrayList<Data> = ArrayList<Data>()
    val LoginActivityIntent = "LOGIN_REQUEST"
    val firebaseDatabasLocation = "https://mycartoonlist-67d5e-default-rtdb.europe-west1.firebasedatabase.app"
}