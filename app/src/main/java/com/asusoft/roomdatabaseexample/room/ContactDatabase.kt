package com.asusoft.roomdatabaseexample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    version = 1,
    entities = [Contact::class],
    autoMigrations = [],
    exportSchema = true
)
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

        @Synchronized
        fun getDatabase(context: Context, scope: CoroutineScope): ContactDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact"
                ).addCallback(ContactDataBaseCallback(scope))
                    .build()
            }

            return INSTANCE!!
        }
    }
}