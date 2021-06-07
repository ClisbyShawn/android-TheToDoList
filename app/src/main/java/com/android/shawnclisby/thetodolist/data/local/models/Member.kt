package com.android.shawnclisby.thetodolist.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * is a representation
 * of an end-user, authentication
 *  process is not recognized with
 *  this model.
 * @author Shawn Clisby
 * */
@Entity(tableName = "member")
data class Member(
    @PrimaryKey val memberId: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
)