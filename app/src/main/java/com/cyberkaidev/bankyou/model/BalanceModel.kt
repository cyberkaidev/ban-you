package com.cyberkaidev.bankyou.model

import kotlinx.serialization.Serializable

@Serializable
data class BalanceModel(
    val usdc: String,
    val usdt: String,
)

@Serializable
data class BalanceResponseModel(
    val balance: BalanceModel
)
