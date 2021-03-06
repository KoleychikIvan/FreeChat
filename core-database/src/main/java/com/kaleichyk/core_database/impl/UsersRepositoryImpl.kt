package com.kaleichyk.core_database.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.kaleichyk.core_database.*
import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.utils.TAG
import com.koleychik.models.constants.UserConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.results.user.UsersResult
import com.koleychik.models.users.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class UsersRepositoryImpl @Inject constructor() : UsersRepository {

    private val store = FirebaseFirestore.getInstance()

    override suspend fun getUsers(orderBy: String, startAfter: Int, limit: Long): UsersResult {
        val collection = store.collection(UserConstants.ROOT_PATH)
            .orderBy(orderBy)
            .startAt(startAfter)
            .limit(limit)
        return try {
            val result = collection.get().await()
            val list = result.toListFromQuerySnapshot(User::class.java)
            Log.d(TAG, "list users = $list")
            UsersResult.Successful(list)
        } catch (e: FirebaseFirestoreException) {
            e.toUsersResultError()
        }
    }

    override suspend fun getUserById(id: String): UserResult {
        val document = store.collection(UserConstants.ROOT_PATH).document(id)

        (return try {
            val result = document.get().await()
            UserResult.Successful(result.getObject(User::class.java))
        } catch (e: FirebaseFirestoreException) {
            e.toUserResultError()
        })
    }

    override suspend fun searchByName(name: String): UsersResult {
        val collection = store.collection(UserConstants.ROOT_PATH)
            .orderBy(UserConstants.NAME)
            .startAt(name).endAt(name + '\uf8ff')

        return try {
            val result = collection.get().await()
            UsersResult.Successful(result.toListFromQuerySnapshot(User::class.java))
        } catch (e: FirebaseFirestoreException) {
            e.toUsersResultError()
        }
    }

    override suspend fun deleteUser(id: String): CheckResult {
        val document = store.collection(UserConstants.ROOT_PATH).document(id)

        return try {
            document.delete()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

//    override suspend fun bindDialogIdToUser(userId: String, dialogId: Long): CheckResult {
//        val document = store.collection(UserConstants.ROOT_PATH).document(userId)
//
//        val listDialogsIds: MutableList<Long> = try {
//            (document.get().await().toObject<User>()?.listDialogsId as MutableList?
//                ?: mutableListOf())
//        } catch (e: NullPointerException) {
//            mutableListOf()
//        } catch (e: FirebaseFirestoreException) {
//            if (e.code != FirebaseFirestoreException.Code.NOT_FOUND) return e.toCheckResultError()
//            else mutableListOf()
//        }
//
//        listDialogsIds.add(dialogId)
//
//        return try {
//            document.update(UserConstants.LIST_DIALOGS_IDS, listDialogsIds).await()
//            CheckResult.Successful
//        } catch (e: FirebaseFirestoreException) {
//            e.toCheckResultError()
//        }
//    }
}