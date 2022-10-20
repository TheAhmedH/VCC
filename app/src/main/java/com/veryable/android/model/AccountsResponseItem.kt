package com.veryable.android.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountsResponseItem(
    val account_name: String,
    val account_type: String,
    val desc: String,
    val id: Int
) : java.io.Serializable