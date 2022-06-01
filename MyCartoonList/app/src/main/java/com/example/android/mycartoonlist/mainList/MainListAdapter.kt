package com.example.android.mycartoonlist.mainList

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycartoonlist.AnimeItemActivity
import com.example.android.mycartoonlist.LoginActivity
import com.example.android.mycartoonlist.R
import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.dataclasses.Data
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainListAdapter(arrayList: ArrayList<Data>, context: Context) : RecyclerView.Adapter<MainListAdapter.MainListHolder>() {
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
                Picasso.get().load(item.thumbnail).into(holder.image)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
        holder.title.text = item.title
        holder.genres.text = item.status
        /// start new activty
        holder.layout.setOnClickListener {
            val intent = Intent(context , AnimeItemActivity::class.java).apply {
                putExtra("data", item)
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