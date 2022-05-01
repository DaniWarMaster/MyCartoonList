package com.example.android.mycartoonlist.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android.mycartoonlist.LoginActivity
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.createAccount.RegisterFragment
import com.example.android.mycartoonlist.login.LoginFragment

class ProfileNotLoggedFragment : Fragment(){
    private lateinit var loginButton: TextView
    private lateinit var createAccountButton : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_not_logged, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton = view.findViewById(R.id.profile_notLogged_fragment_loginButton)
        loginButton.setOnClickListener {
            loginOperation()
        }

        createAccountButton = view.findViewById(R.id.profile_notLogged_fragment_createAccountButton)
        createAccountButton.setOnClickListener {
            createAccountOperation()
        }
    }

    /// ar trebui schimbate altfel
    fun loginOperation() {
        //parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, LoginFragment()).commit()
        val intent = Intent(context, LoginActivity::class.java).apply {
            putExtra(Common.LoginActivityIntent, true)
        }
        startActivity(intent)
    }

    fun createAccountOperation() {
        //parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, RegisterFragment()).commit()
        val intent = Intent(context, LoginActivity::class.java).apply {
            putExtra(Common.LoginActivityIntent, false)
        }
        startActivity(intent)
    }

    companion object {

    }
}