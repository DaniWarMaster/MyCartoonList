package com.example.android.mycartoonlist.dataclasses

import java.io.Serializable
import java.util.*

data class User(
    val id: UUID?,
    val username: String,
    val personalAnimes: List<UserAnime>?
) : Serializable {
    constructor() : this(null, "", null)
}
