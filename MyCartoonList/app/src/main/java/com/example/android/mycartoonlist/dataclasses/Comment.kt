package com.example.android.mycartoonlist.dataclasses

import java.io.Serializable

data class Comment (
 val user: String,
 val comment: String,
 val date: String
) : Serializable {
    constructor() : this("", "", "")
}