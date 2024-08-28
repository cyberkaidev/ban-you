package com.cyberkaidev.bankyou.data.repositories

import com.cyberkaidev.bankyou.model.BalanceModel
import com.cyberkaidev.bankyou.model.BalanceResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BalanceRepository(private val httpClient: HttpClient) {
    companion object {
        private const val BASE_URL = "localhost:33333"
    }

    suspend fun getBalance(key: String): BalanceModel {
        val response = httpClient.get("$BASE_URL/v1/stablecoin/get-balance/$key").body<BalanceResponseModel>()

        return BalanceModel(
            usdc = response.balance.usdc,
            usdt = response.balance.usdt
        )
    }
}