package com.example.android.mycartoonlist.mainList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycartoonlist.AnimeItemActivity
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.comments.CommentsAdapter
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.dataclasses.Comment
import com.example.android.mycartoonlist.dataclasses.Data
import com.example.android.mycartoonlist.reccomandation.ReccomandationSystem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class MainListElementFragment : Fragment() {

    private var commentDatabase = FirebaseDatabase.getInstance(Common.firebaseDatabasLocation).getReference("comments")
    private lateinit var recyclerView: RecyclerView
    private var arrayComment = ArrayList<Comment>()
    private lateinit var commentAdapter : CommentsAdapter

    private lateinit var animeText1: TextView
    private lateinit var animeText2: TextView
    private lateinit var animeText3: TextView
    private lateinit var animeImage1: ImageView
    private lateinit var animeImage2: ImageView
    private lateinit var animeImage3: ImageView
    private lateinit var animeLayout1: RelativeLayout
    private lateinit var animeLayout2: RelativeLayout
    private lateinit var animeLayout3: RelativeLayout

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

    private val reccomandationSystem = ReccomandationSystem()

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

        animeLayout1 = view.findViewById(R.id.mainList_details_recco1)
        animeImage1 = view.findViewById(R.id.mainList_details_recco1_image)
        animeText1 = view.findViewById(R.id.mainList_details_recco1_title)

        animeLayout2 = view.findViewById(R.id.mainList_details_recco2)
        animeImage2 = view.findViewById(R.id.mainList_details_recco2_image)
        animeText2 = view.findViewById(R.id.mainList_details_recco2_title)

        animeLayout3 = view.findViewById(R.id.mainList_details_recco3)
        animeImage3 = view.findViewById(R.id.mainList_details_recco3_image)
        animeText3 = view.findViewById(R.id.mainList_details_recco3_title)

        recyclerView = view.findViewById(R.id.detailsElement_fragment_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        commentAdapter = CommentsAdapter(arrayComment)
        recyclerView.adapter = commentAdapter

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

        val recoo = reccomandationSystem.getReccomandation(item)

        Picasso.get().load(recoo[0].thumbnail).into(animeImage1)
        animeText1.text = recoo[0].title
        animeLayout1.setOnClickListener {
            val intent = Intent(context , AnimeItemActivity::class.java).apply {
                putExtra("data", recoo[0])
            }
            context?.startActivity(intent)
        }

        Picasso.get().load(recoo[1].thumbnail).into(animeImage2)
        animeText2.text = recoo[1].title
        animeLayout2.setOnClickListener {
            val intent = Intent(context , AnimeItemActivity::class.java).apply {
                putExtra("data", recoo[1])
            }
            context?.startActivity(intent)
        }

        Picasso.get().load(recoo[2].thumbnail).into(animeImage3)
        animeText3.text = recoo[2].title
        animeLayout3.setOnClickListener {
            val intent = Intent(context , AnimeItemActivity::class.java).apply {
                putExtra("data", recoo[2])
            }
            context?.startActivity(intent)
        }

        commentDatabase.child(item.title).addValueEventListener(postListener)
    }

    val postListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            arrayComment.clear()
            if(snapshot.exists()) {
                snapshot.children.forEach { child ->
                    //println("-----> Item Found: ${child}")

                    val item : Comment? = child.getValue(Comment::class.java)
                    //println("-----> Item Converted: ${item}")
                    if(item != null) {
                        arrayComment.add(item)
                        //println("-----> Item Inserted")
                    }
                }
            }
            commentAdapter.notifyDataSetChanged()
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Error", "Error at receiving the database")
        }

    }

    companion object {

    }
}