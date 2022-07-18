package com.daniel.codetest.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/*
 * Created by Daniel.
 * The Data Access Object for Phrase entity operations in database.
 */
@Dao
interface UserDataDao {

    @Query("SELECT * FROM PeopleListTable")
    fun getListData(): List<PeoplesInfoListItem>

    @Insert
    fun insertUsersData(coins: List<PeoplesInfoListItem>)

    @Query("DELETE FROM PeopleListTable")
    fun deleteList()

}
