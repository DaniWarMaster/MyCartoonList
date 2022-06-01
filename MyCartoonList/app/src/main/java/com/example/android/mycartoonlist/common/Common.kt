package com.example.android.mycartoonlist.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object Common {
    var isLogged = false
    var user : FirebaseUser? = null
    val LoginActivityIntent = "LOGIN_REQUEST"
    val firebaseDatabasLocation = "https://mycartoonlist-67d5e-default-rtdb.europe-west1.firebasedatabase.app"
}