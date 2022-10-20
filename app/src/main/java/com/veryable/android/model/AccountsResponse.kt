package com.veryable.android.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AccountsResponse : ArrayList<AccountsResponseItem>()