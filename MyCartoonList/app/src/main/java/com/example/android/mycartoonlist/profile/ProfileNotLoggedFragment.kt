package com.example.android.mycartoonlist.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.android.mycartoonlist.LoginActivity
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.createAccount.RegisterFragment
import com.example.android.mycartoonlist.login.LoginFragment
import com.example.android.mycartoonlist.mainList.MainListFragment
import com.google.android.material.navigation.NavigationView

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

        //navigationView = view.findViewById(R.id.navigation_view)

        loginButton = view.findViewById(R.id.profile_notLogged_fragment_loginButton)
        loginButton.setOnClickListener {
            loginOperation()
        }

        createAccountButton = view.findViewById(R.id.profile_notLogged_fragment_createAccountButton)
        createAccountButton.setOnClickListener {
            createAccountOperation()
        }
    }

    private val intentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, MainListFragment()).commit()
                //navigationView.menu.findItem(R.id.nav_login).setVisible(false)
                //navigationView.menu.findItem(R.id.nav_logout).setVisible(true)
                //navigationView.setCheckedItem(R.id.nav_mainList)
            }
        }

    /// ar trebui schimbate altfel
    fun loginOperation() {
        //parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, LoginFragment()).commit()
        val intent = Intent(context, LoginActivity::class.java).apply {
            putExtra(Common.LoginActivityIntent, true)
        }
        //startActivity(intent)
        intentLauncher.launch(intent)
    }

    fun createAccountOperation() {
        //parentFragmentManager.beginTransaction().replace(R.id.drawer_fragment_container, RegisterFragment()).commit()
        val intent = Intent(context, LoginActivity::class.java).apply {
            putExtra(Common.LoginActivityIntent, false)
        }
        //startActivity(intent)
        intentLauncher.launch(intent)
    }

    companion object {

    }
}