package com.veryable.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.veryable.android.model.AccountsResponseItem
import com.veryable.android.model.AccountsResponseSorted
import com.veryable.android.repository.IAccountsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.InjectMocks
import org.mockito.Mockito

class PayoutsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: PayoutsViewModel

    @InjectMocks
    lateinit var repository: IAccountsRepository

    private val accountResponseBank = AccountsResponseItem(
        id = 101,
        account_type = "bank",
        account_name = "WF Checking Account",
        desc = "Wells Fargo (x2356)"
    )

    private val accountResponseCard = AccountsResponseItem(
        id = 102,
        account_type = "card",
        account_name = "WF Debit Card",
        desc = "VISA (x4363)"
    )


    @Before
    fun setUp() {
        repository = Mockito.mock(IAccountsRepository::class.java)
        viewModel = PayoutsViewModel(repository)
    }

    @Test
    fun test01_filterAndSetResponse() {
        val expectedBankElement =
            AccountsResponseSorted(
                "bankItem",
                accountResponseBank, "Bank Account: ACH - Same Day"
            )
        val expectedCardElement =
            AccountsResponseSorted(
                "bankItem",
                accountResponseBank,
                "Bank Account: ACH - Same Day"
            )

        val expectedElements = listOf(expectedBankElement, expectedCardElement)
        val testServerInput = listOf(accountResponseBank, accountResponseCard)

        val resultList = viewModel.filterAndSetResponse(testServerInput)

        expectedElements.forEach {
            Assert.assertTrue(resultList.contains(it))
        }
    }

    @Test
    fun test02_setResponseLiveDataValue() {

        val testFilteredData = listOf<AccountsResponseSorted>(
            AccountsResponseSorted(
                account_heading = "Bank Accounts",
                account = null,
                process = null
            ),
            AccountsResponseSorted("bankItem", accountResponseBank, "Bank Account: ACH - Same Day"),
            AccountsResponseSorted(account_heading = "Cards", account = null, process = null),
            AccountsResponseSorted("bankItem", accountResponseBank, "Bank Account: ACH - Same Day")
        )

        viewModel.setResponseLiveDataValue(testFilteredData)

        Assert.assertEquals(viewModel.accountsResponse.value, testFilteredData)
    }
}