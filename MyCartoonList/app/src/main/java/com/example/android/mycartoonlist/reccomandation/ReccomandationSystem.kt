package com.example.android.mycartoonlist.reccomandation

import com.example.android.mycartoonlist.common.Common
import com.example.android.mycartoonlist.dataclasses.Data
import com.google.firebase.database.FirebaseDatabase

class ReccomandationSystem {

    fun getReccomandation(animeMain: Data) : List<Data> {
        val animesList = Common.animesList
        val animesRezultList = ArrayList<Pair<Data, Double>>()

        animesList.forEach { anime ->
            val score = calculateAnimeScore(animeMain, anime)
            animesRezultList.add(Pair(anime, score))
            //println("---> Animes Reccomended: ${score} - ${anime.title}")
        }

        val sortedList = animesRezultList.sortedWith(compareByDescending({it.second}))

        val aux = sortedList.subList(1, 4)
        val animesRzult = ArrayList<Data>()
        aux.forEach { pair ->
            //println("---> Animes Reccomended: ${pair.second} - ${pair.first.title}")
            animesRzult.add(pair.first)
        }
        return animesRzult
    }

    /// jacarta index
    fun calculateAnimeScore(animeMain: Data, anime: Data) : Double {
        var rezult = 0.0
        var commonTags = 0

        animeMain.tags?.forEach {  tag ->
            if(anime.tags?.contains(tag) == true) {
                commonTags++
            }
        }

        var sizeAnimeMain = 0
        if(animeMain.tags != null ) {
            sizeAnimeMain = animeMain.tags!!.size
        }

        var sizeAnime = 0
        if(anime.tags != null) {
            sizeAnime = anime.tags!!.size
        }

        if(sizeAnimeMain + sizeAnime - commonTags != 0) {
            rezult = (commonTags + 0.0) / (sizeAnimeMain + sizeAnime - commonTags)
        }

        return rezult
    }
}