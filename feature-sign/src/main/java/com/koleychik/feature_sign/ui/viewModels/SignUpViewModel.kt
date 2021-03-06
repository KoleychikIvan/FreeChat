package com.koleychik.feature_sign.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.checkEmail
import com.koleychik.core_authentication.checkName
import com.koleychik.core_authentication.checkPassword
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.states.CheckDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _createAccountState = MutableLiveData<CheckDataState>(null)
    val createAccountState: LiveData<CheckDataState> get() = _createAccountState

    fun startCreateAccount(
        name: String,
        email: String,
        password: String,
        repeatPassword: String
    ) = viewModelScope.launch(Dispatchers.IO) {

        withContext(Dispatchers.Main) {
            _createAccountState.value = CheckDataState.Checking
        }

        val listResult = listOf(
            checkName(name),
            checkEmail(email),
            checkPassword(password, repeatPassword)
        )

        for (i in listResult) {
            if (i is CheckResult.Error) {
                withContext(Dispatchers.Main) {
                    _createAccountState.value = i.toCheckDataState()
                }
                return@launch
            }
        }

        val result = authRepository.createAccount(name, email, password).toCheckDataState()

        withContext(Dispatchers.Main) {
            _createAccountState.value = result
        }
    }

    fun googleSignInUserResult(userResult: UserResult) {
        _createAccountState.value = userResult.toCheckDataState()
    }

}