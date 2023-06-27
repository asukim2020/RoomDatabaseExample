package com.asusoft.roomdatabaseexample

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.asusoft.roomdatabaseexample.room.ContactDatabase
import com.asusoft.roomdatabaseexample.viewmodel.ContactRepository
import com.ipageon.atnt.database.datastore.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class ContactApplication: Application() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_datastore")

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ContactDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ContactRepository(database.contactDao()) }

    override fun onCreate() {
        super.onCreate()

        DataStoreManager.dataStore = dataStore
    }
}