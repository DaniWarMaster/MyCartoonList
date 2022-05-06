package com.example.android.mycartoonlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.createAccount.RegisterFragment
import com.example.android.mycartoonlist.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val loginRequest = intent.getBooleanExtra(Common.LoginActivityIntent, true)

        if(loginRequest) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.drawer_fragment_container, LoginFragment()).commit()
        }
        else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.drawer_fragment_container, RegisterFragment()).commit()
        }
    }
}