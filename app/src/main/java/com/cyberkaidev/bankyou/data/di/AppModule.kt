package com.cyberkaidev.bankyou.data.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.cyberkaidev.bankyou.data.repositories.BalanceRepository
import com.cyberkaidev.bankyou.data.repositories.TransactionsRepository
import com.cyberkaidev.bankyou.data.repositories.UserRepository
import com.cyberkaidev.bankyou.viewmodel.BalanceViewModel
import com.cyberkaidev.bankyou.viewmodel.TransactionsViewModel
import com.cyberkaidev.bankyou.viewmodel.UserViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::BalanceRepository)
    singleOf(::TransactionsRepository)
    singleOf(::UserRepository)
    viewModelOf(::BalanceViewModel)
    viewModelOf(::TransactionsViewModel)
    viewModelOf(::UserViewModel)
}

val storageModule = module {
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("user_preferences")
        }
    }
}

val networkModule = module {
    single {
        HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}