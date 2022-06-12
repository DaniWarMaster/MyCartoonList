package com.example.android.mycartoonlist.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.android.mycartoonlist.LoginActivity
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.common.Tags
import com.example.android.mycartoonlist.createAccount.RegisterFragment
import com.example.android.mycartoonlist.databaseHandlers.UserDatabaseHandler
import com.example.android.mycartoonlist.newsFeed.NewsFeedFragment
import com.example.android.mycartoonlist.recoverPassword.RecoverPasswordFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var loginButton : Button
    private lateinit var forgotPasswordButton : TextView
    private lateinit var createAccountButton : TextView
    private lateinit var username : TextInputEditText
    private lateinit var password : TextInputEditText

    private lateinit var auth: FirebaseAuth
    private val userDatabaseHandler = UserDatabaseHandler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth

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
        val usernameText = username.text.toString()
        val passwordText = password.text.toString()

        auth.signInWithEmailAndPassword(usernameText, passwordText)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Tags.LoginFragment, "signInWithEmail:success")
                    val user = auth.currentUser
                    Common.isLogged = true
                    Common.user = auth.currentUser
                    Common.userData = userDatabaseHandler.getUserByEmail(Common.user!!.email.toString())
                    activity?.setResult(Activity.RESULT_OK)
                    activity?.finish()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Tags.LoginFragment, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }

        //// swap with http request after doing the backend
        /*
        if(usernameText.contentEquals("Daniel") && passwordText.contentEquals("12345")) {
            Toast.makeText(view?.context,"Login Succesfull",Toast.LENGTH_SHORT).show()

            // setting the logged user
            Common.isLogged = true

            // redirecting the user to the newsfeed page
            //parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, NewsFeedFragment()).commit()
            activity?.finish()
        }
        else {
            Toast.makeText(view?.context,"Login Failed",Toast.LENGTH_SHORT).show()
        }
         */
    }

    fun forgotPasswordFunction() {
        //redirecting to the fragment for recovering the password
        parentFragmentManager.beginTransaction()
            .replace(R.id.drawer_fragment_container, RecoverPasswordFragment())
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    fun createAccountFunction() {
        //redirecting to the fragment for creating the account
        parentFragmentManager.beginTransaction()
            .replace(R.id.drawer_fragment_container, RegisterFragment())
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    companion object {

    }
}