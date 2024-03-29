package com.example.android.mycartoonlist.recoverPassword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.login.LoginFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverPasswordFragment : Fragment() {

    private lateinit var recoverButton: Button
    private lateinit var emailAddress : TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recover_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailAddress = view.findViewById(R.id.recoverFragment_email)
        recoverButton = view.findViewById(R.id.recoverFragment_recoverButton)
        recoverButton.setOnClickListener {
            recoverCredentials()
        }

    }

    fun recoverCredentials() {
        Toast.makeText(view?.context, "Email Sent", Toast.LENGTH_SHORT).show()
        ////http request for email sender
        Firebase.auth.sendPasswordResetEmail(emailAddress.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("RecoverPasswordFragment", "sentRecoverEmail:success -> ${emailAddress.text.toString()}")
                }
                else {
                    Log.d("RecoverPasswordFragment", "sentRecoverEmail:failure")
                }
            }
        //// redirecting to login fragment
        parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, LoginFragment()).commit()
    }

    companion object {

    }
}