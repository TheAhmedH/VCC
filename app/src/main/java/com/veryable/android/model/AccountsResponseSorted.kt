package com.veryable.android.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountsResponseSorted(
    var account_heading: String?,
    var account: AccountsResponseItem?,
    var process:String?
) : java.io.Serializable