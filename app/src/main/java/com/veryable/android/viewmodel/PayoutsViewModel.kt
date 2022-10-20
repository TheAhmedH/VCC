package com.veryable.android.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veryable.android.model.AccountsResponseItem
import com.veryable.android.model.AccountsResponseSorted
import com.veryable.android.repository.IAccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayoutsViewModel @Inject constructor(val repository: IAccountsRepository) : ViewModel() {
    private val TAG = "PayoutsViewModel"

     private val _accountsResponseTest: MutableLiveData<List<AccountsResponseSorted>> =
        MutableLiveData()
    val accountsResponse: LiveData<List<AccountsResponseSorted>>
        get() {
            return _accountsResponseTest
        }


    fun getAccountDetails() = viewModelScope.launch {
        val accountsResponse = repository.getAllAccountDetails()
        if (accountsResponse.isSuccessful) {
            accountsResponse.body()?.toList()?.let { serverResponse ->

                setResponseLiveDataValue(filterAndSetResponse(serverResponse))
            }
            Log.d(TAG, "getAccountDetails: ${accountsResponse.body()}")
        } else {
            Log.e(TAG, "getAccountDetails: ")
        }
    }

    fun filterAndSetResponse(
        serverResponse: List<AccountsResponseItem>
    ): List<AccountsResponseSorted> {
        var testList = mutableListOf<AccountsResponseSorted>()
        testList.add(AccountsResponseSorted("Bank Accounts", null, null))

        val filteredBankList = serverResponse.filter { item -> item.account_type == "bank" }
        filteredBankList.forEach {
            testList.add(AccountsResponseSorted("bankItem", it, "Bank Account: ACH - Same Day"))
        }

        val filteredCardList = serverResponse.filter { item -> item.account_type == "card" }
        testList.add(AccountsResponseSorted("Cards", null, null))
        filteredCardList.forEach {
            testList.add(AccountsResponseSorted("cardItem", it, "Card: Instant"))
        }
        return testList
    }


    fun setResponseLiveDataValue(response: List<AccountsResponseSorted>) {
        _accountsResponseTest.value = response
    }

}
