package com.example.android.mycartoonlist.mainList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.dataclasses.Data
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 * Use the [MainListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainListFragment : Fragment() {

    private val database = FirebaseDatabase.getInstance(Common.firebaseDatabasLocation).getReference("data")
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainListAdapter : MainListAdapter
    private lateinit var animesList: ArrayList<Data>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.mainList_fragment_recyclerView)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)

        animesList = ArrayList()
        database.limitToFirst(5).addListenerForSingleValueEvent(postListener)

        mainListAdapter = MainListAdapter(animesList, view.context)
        recyclerView.adapter = mainListAdapter

    }

    val postListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            animesList.clear()
            if(snapshot.exists()) {
                snapshot.children.forEach { child ->
                    println("-----> Item Found: ${child}")

                    val item : Data? = child.getValue(Data::class.java)
                    println("-----> Item Converted: ${item}")
                    if(item != null) {
                        animesList.add(item)
                        println("-----> Item Inserted")
                    }
                }
            }
            mainListAdapter.notifyDataSetChanged()
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Error", "Error at receiving the database")
        }

    }
    companion object {

    }
}