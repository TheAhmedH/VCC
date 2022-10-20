package com.veryable.android.repository

import com.veryable.android.model.AccountsResponse
import com.veryable.android.network.AccountsService
import retrofit2.Response
import javax.inject.Inject

interface IAccountsRepository {
    suspend fun getAllAccountDetails(): Response<AccountsResponse>
}

class AccountsRepository @Inject constructor(val service: AccountsService) : IAccountsRepository {
    override suspend fun getAllAccountDetails(): Response<AccountsResponse> {
        return  service.getAccountDetails()
    }
}