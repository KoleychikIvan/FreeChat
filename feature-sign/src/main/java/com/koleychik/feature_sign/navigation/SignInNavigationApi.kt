package com.koleychik.feature_sign.navigation

import android.os.Bundle

interface SignInNavigationApi {

    fun fromSignInFragmentToMainScreen()
    fun fromSignInFragmentToPasswordRecovery(bundle: Bundle? = null)
    fun fromSignInFragmentToSignUp(bundle: Bundle? = null)

}