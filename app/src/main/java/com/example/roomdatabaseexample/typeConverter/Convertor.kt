package com.example.roomdatabaseexample.typeConverter

import androidx.room.TypeConverter
import java.util.*


class Convertor {

    @TypeConverter
    fun fromDateToLong(date: Date):Long{
        return date.time
    }

    @TypeConverter
    fun fromLongToDate(value: Long):Date{
        return Date(value)
    }
}