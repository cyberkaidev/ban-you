package com.cyberkaidev.bankyou.data.repositories

import com.cyberkaidev.bankyou.model.TransactionsModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TransactionsRepository(private val httpClient: HttpClient) {
    companion object {
        private const val BASE_URL = "localhost:33333"
    }

    suspend fun getTransactions(key: String): TransactionsModel {
        val response = httpClient.get("$BASE_URL/v1/stablecoin/get-transactions/$key").body<TransactionsModel>()
        return TransactionsModel( transactions = response.transactions )
    }
}