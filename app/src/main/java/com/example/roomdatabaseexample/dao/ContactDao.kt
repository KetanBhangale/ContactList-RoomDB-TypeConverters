package com.example.roomdatabaseexample.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdatabaseexample.model.Contact

@Dao
interface ContactDao {
    @Insert
    suspend fun insertContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("select * from contacts")
    fun getContacts():LiveData<List<Contact>>
}