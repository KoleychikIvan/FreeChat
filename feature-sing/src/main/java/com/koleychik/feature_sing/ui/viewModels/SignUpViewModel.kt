package com.koleychik.feature_sing.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_authorization.checkEmail
import com.koleychik.core_authorization.checkName
import com.koleychik.core_authorization.checkPassword
import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.core_authorization.result.CheckResult
import com.koleychik.core_authorization.result.user.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    val isLoading = MutableLiveData(false)
    val userResult = MutableLiveData<UserResult>(null)
    val checkResult = MutableLiveData<CheckResult>(null)

    fun startCreateAccount(
        name: String,
        email: String,
        password: String,
        repeatPassword: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        isLoading.value = true
        val listResult = listOf(
            checkName(name),
            checkEmail(email),
            checkPassword(password, repeatPassword)
        )

        for (i in listResult) {
            if (i !is CheckResult.Successful) {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                    checkResult.value = i
                }
                break
            }
        }

        withContext(Dispatchers.Main) {
            authRepository.createAccount(name, email, password) {
                userResult.value = it
                isLoading.value = false
            }
        }
    }
}