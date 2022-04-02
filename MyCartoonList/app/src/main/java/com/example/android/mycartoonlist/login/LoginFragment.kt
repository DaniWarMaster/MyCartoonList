package com.example.android.mycartoonlist.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.createAccount.RegisterFragment
import com.example.android.mycartoonlist.newsFeed.NewsFeedFragment
import com.example.android.mycartoonlist.recoverPassword.RecoverPasswordFragment
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private lateinit var loginButton : Button
    private lateinit var forgotPasswordButton : TextView
    private lateinit var createAccountButton : TextView
    private lateinit var username : TextInputEditText
    private lateinit var password : TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton = view.findViewById(R.id.loginFragment_loginButton)
        loginButton.setOnClickListener {
            loginFunction()
        }

        forgotPasswordButton = view.findViewById(R.id.loginFragment_forgotAccountButton)
        forgotPasswordButton.setOnClickListener {
            forgotPasswordFunction()
        }

        createAccountButton = view.findViewById(R.id.loginFragment_createAccountButton)
        createAccountButton.setOnClickListener {
            createAccountFunction()
        }

        username = view.findViewById(R.id.loginFragment_username)
        password = view.findViewById(R.id.loginFragment_password)
    }

    fun loginFunction() {
        val usernameText = username.text
        val passwordText = password.text

        //// swap with http request after doing the backend
        if(usernameText.contentEquals("Daniel") && passwordText.contentEquals("12345")) {
            Toast.makeText(view?.context,"Login Succesfull",Toast.LENGTH_SHORT).show()

            // setting the logged user
            Common.isLogged = true

            // redirecting the user to the newsfeed page
            parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, NewsFeedFragment()).commit()
        }
        else {
            Toast.makeText(view?.context,"Login Failed",Toast.LENGTH_SHORT).show()
        }
    }

    fun forgotPasswordFunction() {
        //redirecting to the fragment for recovering the password
        parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, RecoverPasswordFragment()).commit()
    }

    fun createAccountFunction() {
        //redirecting to the fragment for creating the account
        parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, RegisterFragment()).commit()
    }

    companion object {

    }
}