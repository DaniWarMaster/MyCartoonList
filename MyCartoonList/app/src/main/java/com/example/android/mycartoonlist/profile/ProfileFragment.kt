package com.example.android.mycartoonlist.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.log

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
        timeSpentInfo = view.findViewById(R.id.profile_fragment_timeSpentInfo)

        watchingInfo = view.findViewById(R.id.profile_fragment_watchingInfo)
        completedInfo = view.findViewById(R.id.profile_fragment_completedInfo)
        onHoldInfo = view.findViewById(R.id.profile_fragment_onHoldInfo)
        droppedInfo = view.findViewById(R.id.profile_fragment_droppedInfo)
        planedToWatchInfo = view.findViewById(R.id.profile_fragment_plannedToWatchInfo)

        loadProfileData()
    }

    fun loadProfileData() {
        /// http request
        if(Common.userData!=null) {
            usernameTitle.text = Common.userData!!.username
            usernameInfo.text = Common.userData!!.username
            emailInfo.text = Common.userData!!.email
            dateJoinedInfo.text = Common.userData!!.joinedDate

            var meanScoreAct = 0.0
            var timeSpent = 0.0
            var animesNr = 0
            var watchedAct = 0
            var onHoldAct = 0
            var watchingAct = 0
            var dropeedAct = 0
            var plannedAct = 0
            Common.userData!!.personalAnimes?.forEach { anime ->
                if(anime.score!=0) {
                    meanScoreAct+=anime.score
                    animesNr++
                }
                when(anime.category) {
                    "WATCHED" -> {
                        timeSpent += anime.anime!!.episodes
                        watchedAct++
                    }
                    "PLANNED TO WATCH" -> {
                        plannedAct++
                    }
                    "DROPEED" -> {
                        dropeedAct++
                    }
                    "WATHCING" -> {
                        watchingAct++
                    }
                    "ON HOLD" -> {
                        onHoldAct++
                    }
                }
            }
            meanScoreAct /= animesNr
            timeSpent = timeSpent * 22.5 / 60 / 24

            meanScoreInfo.text = meanScoreAct.toString()
            timeSpentInfo.text = String.format("%.2f", timeSpent) + " days"

            completedInfo.text = watchedAct.toString()
            watchingInfo.text = watchingAct.toString()
            planedToWatchInfo.text = plannedAct.toString()
            droppedInfo.text = dropeedAct.toString()
            onHoldInfo.text = onHoldAct.toString()

            Log.d("TAG", "loadProfileData: ${LocalDateTime.now()}")

        }
        Toast.makeText(view?.context, "Profile Loaded", Toast.LENGTH_SHORT).show()
    }

    companion object {

    }
}