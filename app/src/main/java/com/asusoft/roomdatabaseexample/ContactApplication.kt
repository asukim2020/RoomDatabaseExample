package com.asusoft.roomdatabaseexample

import android.app.Application
import com.asusoft.roomdatabaseexample.room.ContactDatabase
import com.asusoft.roomdatabaseexample.viewmodel.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ContactDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ContactRepository(database.contactDao()) }
}