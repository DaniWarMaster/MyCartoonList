package com.example.android.mycartoonlist.dataclasses

import java.io.Serializable

data class Data(
    val animeSeason: AnimeSeason?,
    val episodes: Int,
    val picture: String,
    val relations: List<String>?,
    val sources: List<String>?,
    val status: String,
    val synonyms: List<String>?,
    val tags: List<String>?,
    val thumbnail: String,
    val title: String,
    val type: String
) : Serializable {
    constructor() : this(null, 0, "", null, null, "", null, null, "", "", "")
}