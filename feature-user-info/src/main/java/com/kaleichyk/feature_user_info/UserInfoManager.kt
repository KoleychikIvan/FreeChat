package com.kaleichyk.feature_user_info

import android.net.Uri
import com.kaleichyk.core_cloud_storage.framework.CloudStorageRepository
import com.kaleichyk.core_cloud_storage.framework.results.UploadResult
import com.kaleichyk.core_cloud_storage.framework.results.toCheckResult
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.utils.CurrentUser
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.models.dialog.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import javax.inject.Inject

internal class UserInfoManager @Inject constructor(
    private val usersRepository: UsersRepository,
    private val dialogsRepository: DialogsRepository,
    private val accountRepository: AccountRepository,
    private val cloudStorageRepository: CloudStorageRepository,
) {

    suspend fun getUserById(userId: String) = usersRepository.getUserById(userId)

    suspend fun deleteUser(id: String): CheckResult {
        val result = accountRepository.deleteUser()
        if (result !is CheckResult.Successful) return result
        return usersRepository.deleteUser(id)
    }

    suspend fun createNewDialog(dialog: Dialog): DialogResult {
        return dialogsRepository.addDialog(dialog.toDialogDTO())
    }

    fun signOut() {
        accountRepository.signOut()
    }

    suspend fun setName(name: String): CheckResult = accountRepository.updateName(name)


    suspend fun setEmail(email: String) = accountRepository.updateEmail(email)


    suspend fun setPassword(password: String) = accountRepository.updatePassword(password)


    suspend fun setIcon(userId: String, uri: Uri, contextType: String?): CheckResult {

        val uploadImageResult = cloudStorageRepository.addImage(userId, uri, contextType)

        if (uploadImageResult is UploadResult.Successful) {
            val updateIconResult = accountRepository.updateIcon(uploadImageResult.uri)
            return if (updateIconResult is CheckResult.Successful) {
                CurrentUser.user?.icon = uploadImageResult.uri.toString()
                updateIconResult
            } else updateIconResult
        }
        return uploadImageResult.toCheckResult()
    }

    suspend fun setBackground(userId: String, uri: Uri, contextType: String?): CheckResult {
        val uploadImageResult = cloudStorageRepository.addImage(userId, uri, contextType)

        if (uploadImageResult is UploadResult.Successful) {
            val updateBackgroundResult = accountRepository.updateBackground(uploadImageResult.uri)
            return if (updateBackgroundResult is CheckResult.Successful) {
                CurrentUser.user?.background = uploadImageResult.uri.toString()
                updateBackgroundResult
            } else updateBackgroundResult
        }

        return uploadImageResult.toCheckResult()
    }
}