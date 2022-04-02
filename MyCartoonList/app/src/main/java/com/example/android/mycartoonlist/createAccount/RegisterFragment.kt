package com.example.android.mycartoonlist.createAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.login.LoginFragment
import com.google.android.material.textfield.TextInputEditText


class RegisterFragment : Fragment() {

    private lateinit var registerButton: Button
    private lateinit var username : TextInputEditText
    private lateinit var password : TextInputEditText
    private lateinit var confirmPassword : TextInputEditText
    private lateinit var email : TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val usernameText = username.text
        val passwordText = password.text
        val confirmPasswordText = confirmPassword.text
        val emailText = email.text

        Toast.makeText(view?.context, "User Registered", Toast.LENGTH_SHORT).show()
        // http request for register

        //// redirecting to login fragment
        parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, LoginFragment()).commit()
    }

    companion object {

    }
}