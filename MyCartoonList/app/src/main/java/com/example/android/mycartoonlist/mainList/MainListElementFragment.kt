package com.example.android.mycartoonlist.mainList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.dataclasses.Data
import com.squareup.picasso.Picasso

class MainListElementFragment : Fragment() {

    private lateinit var scor : TextView
    private lateinit var nrOfEpisodes : TextView
    private lateinit var rank : TextView
    private lateinit var type : TextView
    private lateinit var title : TextView
    private lateinit var genres : TextView
    private lateinit var summary : TextView
    private lateinit var aired : TextView
    private lateinit var season : TextView
    private lateinit var source : TextView
    private lateinit var rating : TextView
    private lateinit var studio : TextView
    private lateinit var image : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_list_element, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scor = view.findViewById(R.id.mainList_details_scoreTop)
        nrOfEpisodes = view.findViewById(R.id.mainList_details_nrEpisodesTop)
        rank = view.findViewById(R.id.mainList_details_rankTop)
        type = view.findViewById(R.id.mainList_details_typeTop)
        title = view.findViewById(R.id.mainList_details_title)
        genres = view.findViewById(R.id.mainList_details_genres)
        summary = view.findViewById(R.id.mainList_details_summary)
        aired = view.findViewById(R.id.mainList_details_aired)
        season = view.findViewById(R.id.mainlist_details_season)
        source = view.findViewById(R.id.mainList_details_source)
        rating = view.findViewById(R.id.mainList_details_rating)
        studio = view.findViewById(R.id.mainlist_details_studio)
        image = view.findViewById(R.id.mainList_details_image)

        if(requireActivity().intent.hasExtra("data")) {
            val item : Data = requireActivity().intent.getSerializableExtra("data") as Data
            setData(item)
        }

    }

    private fun setData(item: Data) {
        scor.text = "-"
        rank.text = "-"
        rating.text = "-"
        studio.text = "-"

        nrOfEpisodes.text = item.episodes.toString()
        type.text = item.type
        title.text = item.title
        var genresAux = "| "
        item.tags?.forEach {
            genresAux += "${it} | "
        }
        genres.text = genresAux

        var summaryAux = ""
        item.relations?.forEach {
            summaryAux += "${it} \n"
        }
        summary.text = summaryAux

        aired.text = item.animeSeason?.year.toString()
        season.text = item.animeSeason?.season

        source.text = item.sources?.get(0).toString()

        Picasso.get().load(item.picture).into(image)
    }

    companion object {

    }
}