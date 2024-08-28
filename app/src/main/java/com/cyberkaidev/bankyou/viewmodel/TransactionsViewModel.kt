package com.cyberkaidev.bankyou.viewmodel

import androidx.lifecycle.ViewModel
import com.cyberkaidev.bankyou.model.TransactionsModel
import com.cyberkaidev.bankyou.data.repositories.TransactionsRepository
import com.cyberkaidev.bankyou.model.NetworkStatusModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TransactionsViewModel(
    private val repository: TransactionsRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow(TransactionsModel(transactions = ArrayList()))
    val transactions = _transactions.asStateFlow()

    suspend fun getTransactions(key: String): NetworkStatusModel {
        try {
            val response = repository.getTransactions(key)
            _transactions.value = response
            return NetworkStatusModel.SUCCESS
        } catch (error: Throwable) {
            throw IllegalArgumentException("Error on getTransactions")
        }
    }
}