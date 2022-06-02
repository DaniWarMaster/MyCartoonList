package com.example.android.mycartoonlist.dataclasses

import java.io.Serializable

data class UserAnime(
    val anime: Data?,
    val score: Int,
    val category: String,
    val review: String
) : Serializable {
    constructor() : this(null, 0, "", "")
}
