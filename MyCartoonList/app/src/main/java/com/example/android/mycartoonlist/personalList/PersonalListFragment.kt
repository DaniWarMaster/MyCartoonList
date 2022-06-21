package com.example.android.mycartoonlist.personalList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.dataclasses.Data


class PersonalListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var personalListAdapter: PersonalListAdapter

    private lateinit var recyclerViewFilter: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.personalList_fragment_recyclerView)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)

        recyclerViewFilter = view.findViewById(R.id.personalList_fragment_recyclerView_filter)
        recyclerViewFilter.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)



        if(Common.isLogged && Common.userData != null && Common.userData!!.personalAnimes != null) {
            personalListAdapter = PersonalListAdapter(Common.userData!!.personalAnimes!!, view.context)
            recyclerView.adapter = personalListAdapter
        }
        else {
            Toast.makeText(view.context, "User Not Logged",Toast.LENGTH_LONG).show()
        }
    }

    companion object {

    }
}