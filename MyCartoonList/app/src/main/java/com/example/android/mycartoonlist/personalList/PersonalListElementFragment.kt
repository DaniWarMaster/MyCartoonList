package com.example.android.mycartoonlist.personalList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.comments.CommentsAdapter
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.dataclasses.Comment
import com.example.android.mycartoonlist.dataclasses.Data
import com.example.android.mycartoonlist.dataclasses.UserAnime
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.time.LocalDateTime

class PersonalListElementFragment : Fragment() {

    private var commentDatabase = FirebaseDatabase.getInstance(Common.firebaseDatabasLocation).getReference("comments")

    private lateinit var scor: TextView
    private lateinit var nrOfEpisodes: TextView
    private lateinit var rank: TextView
    private lateinit var type: TextView
    private lateinit var title: TextView
    private lateinit var genres: TextView
    private lateinit var summary: TextView
    private lateinit var aired: TextView
    private lateinit var season: TextView
    private lateinit var source: TextView
    private lateinit var rating: TextView
    private lateinit var studio: TextView
    private lateinit var image: ImageView

    private lateinit var addComment: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_list_element, container, false)
    }

    private lateinit var userAnime: UserAnime
    private var arrayComment = ArrayList<Comment>()
    private lateinit var commentAdapter : CommentsAdapter

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

        recyclerView = view.findViewById(R.id.detailsElement_fragment_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        commentAdapter = CommentsAdapter(arrayComment)
        recyclerView.adapter = commentAdapter

        addComment = view.findViewById(R.id.mainList_details_addComment)
        addComment.setOnClickListener {
            if(Common.isLogged) {
                val builder = AlertDialog.Builder(view.context).create()
                val view = layoutInflater.inflate(R.layout.add_comment_dialog_box_layout,null)
                val buttonDismiss = view.findViewById<Button>(R.id.dialogDismiss_button)
                val buttonAccept = view.findViewById<Button>(R.id.dialogAccept_button)
                val insertedComment = view.findViewById<TextView>(R.id.addComment_textInput)

                builder.setView(view)
                buttonDismiss.setOnClickListener {
                    builder.dismiss()
                }

                buttonAccept.setOnClickListener {
                    val comment = insertedComment.text.toString()
                    commentDatabase.child(userAnime.anime!!.title).child(Common.userData!!.id.toString()).setValue(Comment(Common.userData!!.username, comment, LocalDateTime.now().toString()))
                    builder.dismiss()
                }


                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }
            else {
                Toast.makeText(view.context, "Please login to use this feature", Toast.LENGTH_SHORT).show()
            }
        }

        if (requireActivity().intent.hasExtra("personalData")) {
           userAnime =
                requireActivity().intent.getSerializableExtra("personalData") as UserAnime
            setData(userAnime)
        }

    }

    val postListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            arrayComment.clear()
            if(snapshot.exists()) {
                snapshot.children.forEach { child ->
                    //println("-----> Item Found: ${child}")

                    val item : Comment? = child.getValue(Comment::class.java)
                    //println("-----> Item Converted: ${item}")
                    if(item != null && child.key.equals(Common.userData!!.id)) {
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

    private fun setData(animeData: UserAnime) {
        val anime = animeData.anime!!
        scor.text = animeData.score.toString()
        rank.text = animeData.category
        rating.text = "-"
        studio.text = "-"

        nrOfEpisodes.text = anime.episodes.toString()
        type.text = anime.type
        title.text = anime.title
        var genresAux = "| "
        anime.tags?.forEach {
            genresAux += "${it} | "
        }
        genres.text = genresAux

        var summaryAux = ""
        anime.relations?.forEach {
            summaryAux += "${it} \n"
        }
        summary.text = summaryAux

        aired.text = anime.animeSeason?.year.toString()
        season.text = anime.animeSeason?.season

        source.text = anime.sources?.get(0).toString()

        Picasso.get().load(anime.picture).into(image)

        commentDatabase.child(userAnime.anime!!.title).addValueEventListener(postListener)
    }

    companion object {

    }
}