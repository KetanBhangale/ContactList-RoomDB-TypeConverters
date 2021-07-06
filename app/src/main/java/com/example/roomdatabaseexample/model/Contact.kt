package com.example.roomdatabaseexample.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "contacts")
data class Contact(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val mobile: String,
    val date: Date,
    val gender: String

)