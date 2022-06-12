package com.example.android.mycartoonlist.createAccount

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.common.Tags
import com.example.android.mycartoonlist.databaseHandlers.UserDatabaseHandler
import com.example.android.mycartoonlist.dataclasses.User
import com.example.android.mycartoonlist.login.LoginFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {

    private lateinit var registerButton: Button
    private lateinit var username : TextInputEditText
    private lateinit var password : TextInputEditText
    private lateinit var confirmPassword : TextInputEditText
    private lateinit var email : TextInputEditText

    private val userDatabase = UserDatabaseHandler()

    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = view.findViewById(R.id.registerFragment_username)
        password = view.findViewById(R.id.registerFragment_password)
        confirmPassword = view.findViewById(R.id.registerFragment_confirmPassword)
        email = view.findViewById(R.id.registerFragment_email)
        registerButton = view.findViewById(R.id.registerFragment_registerButton)
        registerButton.setOnClickListener {
            registerFunction()
        }
    }

    fun registerFunction() {
        val usernameText = username.text.toString()
        val passwordText = password.text.toString()
        val confirmPasswordText = confirmPassword.text.toString()
        val emailText = email.text.toString()

        //Toast.makeText(view?.context, "User Registered", Toast.LENGTH_SHORT).show()
        // http request for register

        if(passwordText.equals(confirmPasswordText)) {
            auth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Tags.RegisterFragment, "createUserWithEmail:success")
                        Toast.makeText(
                            context, "Authentication success.",
                            Toast.LENGTH_SHORT
                        ).show()

                        ///// add username
                        val user = auth.currentUser
                        val profileUpdate = userProfileChangeRequest {
                            displayName = usernameText
                        }
                        user?.updateProfile(profileUpdate)?.addOnCompleteListener {
                            if(it.isSuccessful) {
                                Log.d(Tags.RegisterFragment, "createUserWithEmail:usernameAdded")
                            }

                        }
                        //// add user to db
                        println("Adding User ---> register fragment")
                        userDatabase.addUser(User(null, usernameText, emailText, 0, null))

                        //// redirecting to login fragment
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.drawer_fragment_container, LoginFragment()).commit()
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(Tags.RegisterFragment, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //updateUI(null)
                    }
                }
        }
        else {
            Toast.makeText(requireContext(),"Password do not match.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

    }
}