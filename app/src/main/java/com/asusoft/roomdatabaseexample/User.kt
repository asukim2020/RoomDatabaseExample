package com.asusoft.roomdatabaseexample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
) {

    override fun toString(): String {
        return "User(uid=$uid, firstName=$firstName, lastName=$lastName)"
    }
}