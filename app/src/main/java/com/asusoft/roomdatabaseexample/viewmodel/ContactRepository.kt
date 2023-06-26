package com.asusoft.roomdatabaseexample.viewmodel

import androidx.annotation.WorkerThread
import com.asusoft.roomdatabaseexample.room.Contact
import com.asusoft.roomdatabaseexample.room.ContactDAO
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDAO) {

    val allContacts: Flow<List<Contact>> = contactDao.getAll()

    @WorkerThread
    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    @WorkerThread
    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }
}