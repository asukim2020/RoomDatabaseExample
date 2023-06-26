package com.asusoft.roomdatabaseexample.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDAO

    private class ContactDataBaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val contactDao = database.contactDao()
                    contactDao.deleteAll()
                }
            }
        }
    }

    companion object {
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ContactDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact"
                ).addCallback(ContactDataBaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}