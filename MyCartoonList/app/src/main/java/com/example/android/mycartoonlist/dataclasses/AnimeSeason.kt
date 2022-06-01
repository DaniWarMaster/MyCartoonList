package com.example.android.mycartoonlist.dataclasses

import java.io.Serializable

data class AnimeSeason(
    val season: String,
    val year: Int
) : Serializable {
    constructor() : this("", 0)
}