package com.koleychik.core_authentication.extencions

import android.content.Intent
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.result.GoogleSignInResult

fun AppCompatActivity.loginUsingGoogle(
    res: (GoogleSignInResult) -> Unit
) {
    val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val signInClient = GoogleSignIn.getClient(this, options)
    getGoogleAuthResult(activityResultRegistry, signInClient.signInIntent, res)
}

private fun getGoogleAuthResult(
    activityResultRegistry: ActivityResultRegistry,
    singInIntent: Intent,
    res: (GoogleSignInResult) -> Unit
) {
    val test = activityResultRegistry.register(
        "GOOGLE AUTH",
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task, res)
    }
    test.launch(singInIntent)
}

private fun handleSignInResult(
    completedTask: Task<GoogleSignInAccount>,
    res: (GoogleSignInResult) -> Unit
) {
    var account: GoogleSignInAccount? = null
    try {
        account = completedTask.getResult(ApiException::class.java)
    } catch (e: ApiException) {
        res(GoogleSignInResult.ServerError(e.message.toString()))
    }

    if (account == null) {
        res(GoogleSignInResult.DataError(R.string.cannot_get_information_about_user))
    } else GoogleSignInResult.Successful(
        credential = GoogleAuthProvider.getCredential(account.idToken, null),
        account
    )
}