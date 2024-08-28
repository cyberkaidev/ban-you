package com.cyberkaidev.bankyou.viewmodel

import androidx.lifecycle.ViewModel
import com.cyberkaidev.bankyou.data.repositories.UserRepository

class UserViewModel(
    private val repository: UserRepository
): ViewModel() {
    val address = repository.address

    suspend fun setAddress(value: String) {
        repository.setAddress(value)
    }
}