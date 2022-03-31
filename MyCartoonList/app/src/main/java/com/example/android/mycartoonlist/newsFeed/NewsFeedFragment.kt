package com.example.android.mycartoonlist.newsFeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.mycartoonlist.R

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    companion object {

    }
}