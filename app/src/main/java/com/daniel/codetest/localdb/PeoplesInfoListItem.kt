package com.daniel.codetest.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Daniel.
 *
 * Entity class to store in Room Database
 */
@Entity(tableName = "PeopleListTable")
data class PeoplesInfoListItem(
    @PrimaryKey
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("favouriteColor")
    val favouriteColor: String = "",
    @SerializedName("firstName")
    val firstName: String = "",
    @SerializedName("fromName")
    val fromName: String = "",
    @SerializedName("jobtitle")
    val jobtitle: String = "",
    @SerializedName("lastName")
    val lastName: String = "",
    @SerializedName("to")
    val to: String = ""
)