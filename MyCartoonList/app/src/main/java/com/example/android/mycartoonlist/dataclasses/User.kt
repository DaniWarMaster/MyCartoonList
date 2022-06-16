package com.example.android.mycartoonlist.dataclasses

import java.io.Serializable

data class User(
    val id: String?,
    val username: String,
    val email: String,
    val joinedDate: String,
    var animesNumber: Int,
    val personalAnimes: List<UserAnime>?
) : Serializable {
    constructor() : this(null, "", "", "",0,null)
}
