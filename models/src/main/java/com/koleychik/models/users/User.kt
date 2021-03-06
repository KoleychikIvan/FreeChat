package com.koleychik.models.users

import android.os.Parcelable
import com.koleychik.models.dialog.DialogDTO
import kotlinx.parcelize.Parcelize

@Parcelize
open class User(
    open val id: String,
    open var name: String,
    open val email: String,
    open val createdAt: Long,
    open val isOnline: Boolean,
    open val icon: String? = null,
    open val background: String? = null,
) : Parcelable {

    constructor(id: String, name: String, email: String, createdAt: Long, isOnline: Boolean) : this(
        id = id,
        name = name,
        email = email,
        createdAt = createdAt,
        isOnline = isOnline,
        icon = null
    )

    constructor() : this(
        id = "null",
        name = "null",
        email = "null",
        createdAt = 0,
        isOnline = false,
    )

    fun toDialogMember() = DialogDTO.Member(id, name, icon)

    fun asRoot() = UserRoot(
        id,
        name,
        email,
        createdAt,
        isOnline,
        icon,
        background
    )
}