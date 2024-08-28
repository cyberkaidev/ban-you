package com.cyberkaidev.bankyou.viewmodel

import androidx.lifecycle.ViewModel
import com.cyberkaidev.bankyou.model.BalanceModel
import com.cyberkaidev.bankyou.data.repositories.BalanceRepository
import com.cyberkaidev.bankyou.model.NetworkStatusModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BalanceViewModel(
    private val repository: BalanceRepository
) : ViewModel() {

    private val _balance = MutableStateFlow(BalanceModel(usdc = "", usdt = ""))
    val balance = _balance.asStateFlow()

    suspend fun getBalance(key: String): NetworkStatusModel {
        try {
            val response = repository.getBalance(key)
            _balance.value = response
            return NetworkStatusModel.SUCCESS
        } catch (error: Throwable) {
            throw IllegalArgumentException("Error on getBalance")
        }
    }
}
