package com.veryable.android.network

import com.veryable.android.model.AccountsResponse
import retrofit2.Response
import retrofit2.http.GET

interface AccountsService {

    @GET("/veryable.json")
    suspend fun getAccountDetails(): Response<AccountsResponse>
}