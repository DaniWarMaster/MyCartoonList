package com.example.android.mycartoonlist.personalList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycartoonlist.*
import com.example.android.mycartoonlist.dataclasses.UserAnime
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PersonalListAdapter(arrayList: List<UserAnime>, context: Context) : RecyclerView.Adapter<PersonalListAdapter.MainListHolder>() {
    var data = arrayList
    private val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_list_element, parent, false)

        return MainListHolder(view)
    }

    override fun onBindViewHolder(holder: MainListHolder, position: Int) {
        val item = data[position]

        holder.meanScore.text = "-"
        GlobalScope.launch(Dispatchers.Main) {
            try {
                /*
                val inImage = java.net.URL(item.thumbnail).openStream()
                val image = BitmapFactory.decodeStream(inImage)
                holder.image.setImageBitmap(image)
                */
                Picasso.get().load(item.anime!!.thumbnail).into(holder.image)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
        holder.title.text = item.anime!!.title
        holder.genres.text = item.anime!!.status
        /// start new activty
        holder.layout.setOnClickListener {
            println()
            val intent = Intent(context , AnimeItemActivity2::class.java).apply {
                putExtra("personalData", item)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = data.size

    class MainListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val meanScore: TextView = itemView.findViewById(R.id.mainList_element_meanScore)
        val image: ImageView = itemView.findViewById(R.id.mainlist_element_image)
        val title: TextView = itemView.findViewById(R.id.mainList_element_title)
        val genres: TextView = itemView.findViewById(R.id.mainList_element_genres)
        val layout: RelativeLayout = itemView.findViewById(R.id.mainList_element_layout)
    }

}