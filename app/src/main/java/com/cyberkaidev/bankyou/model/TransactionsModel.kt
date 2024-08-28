package com.cyberkaidev.bankyou.model

import kotlinx.serialization.Serializable

@Serializable
enum class TokenNameModel { USDC, USDT }

@Serializable
enum class TransactionSubtypeModel { incoming, outgoing }

@Serializable
data class ItemTransactionModel(
    val tokenId: String? = null,
    val amount: String,
    val transactionSubtype: TransactionSubtypeModel,
    val tokenName: TokenNameModel,
    val timestamp: Long,
    val hours: String,
    val date: String,
)

@Serializable
data class CategoryTransactionModel(
    val date: String,
    val items: List<ItemTransactionModel>
)

@Serializable
data class TransactionsModel(
    val transactions: List<CategoryTransactionModel>,
)