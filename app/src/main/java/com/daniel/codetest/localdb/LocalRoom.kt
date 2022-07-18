package com.daniel.codetest.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Created by Daniel.
 */
@Database(entities = [PeoplesInfoListItem::class], version = 1)
abstract class DataInRoom : RoomDatabase() {

    /**
     * This is the Phrase data access object instance
     * @return the dao to phrase database operations
     */
    abstract fun infoListDataDao(): UserDataDao

    companion object {

        /**
         * This is just for singleton pattern
         */
        private var INSTANCE: DataInRoom? = null

        fun getDatabase(context: Context): DataInRoom {
            if (INSTANCE == null) {
                synchronized(DataInRoom::class.java) {
                    if (INSTANCE == null) {
                        // Get PhraseRoomDatabase database instance
                        Room.databaseBuilder(
                            context.applicationContext,
                            DataInRoom::class.java, "CodeTestDB",
                        )
                            .allowMainThreadQueries()
                            .build().also { INSTANCE = it }
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
