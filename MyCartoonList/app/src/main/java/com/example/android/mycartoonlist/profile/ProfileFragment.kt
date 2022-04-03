package com.example.android.mycartoonlist.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common

class ProfileFragment : Fragment() {

    private lateinit var usernameTitle: TextView

    private lateinit var usernameInfo: TextView
    private lateinit var emailInfo: TextView
    private lateinit var dateJoinedInfo: TextView

    private lateinit var timeSpentInfo: TextView
    private lateinit var meanScoreInfo: TextView

    private lateinit var watchingInfo: TextView
    private lateinit var completedInfo: TextView
    private lateinit var onHoldInfo: TextView
    private lateinit var droppedInfo: TextView
    private lateinit var planedToWatchInfo: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameTitle = view.findViewById(R.id.profile_fragment_usernameGreeting)

        usernameInfo = view.findViewById(R.id.profile_fragment_usernameInfo)
        emailInfo = view.findViewById(R.id.profile_fragment_emailInfo)
        dateJoinedInfo = view.findViewById(R.id.profile_fragment_joinedDateInfo)

        meanScoreInfo = view.findViewById(R.id.profile_fragment_meanScore)
        timeSpentInfo = view.findViewById(R.id.profile_fragment_timeSpentText)

        watchingInfo = view.findViewById(R.id.profile_fragment_watchingInfo)
        completedInfo = view.findViewById(R.id.profile_fragment_completedInfo)
        onHoldInfo = view.findViewById(R.id.profile_fragment_onHoldInfo)
        droppedInfo = view.findViewById(R.id.profile_fragment_droppedInfo)
        planedToWatchInfo = view.findViewById(R.id.profile_fragment_plannedToWatchInfo)

        loadProfileData()
    }

    fun loadProfileData() {
        /// http request
        Toast.makeText(view?.context, "Profile Loaded", Toast.LENGTH_SHORT).show()
    }

    companion object {

    }
}