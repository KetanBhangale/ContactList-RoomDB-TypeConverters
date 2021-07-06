package com.example.roomdatabaseexample.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomdatabaseexample.model.Contact
import com.example.roomdatabaseexample.dao.ContactDao
import com.example.roomdatabaseexample.typeConverter.Convertor

@Database(entities = [Contact::class], version = 2)
@TypeConverters(Convertor::class)
abstract class ConactDatabase: RoomDatabase() {

    abstract fun getContactDao(): ContactDao

    companion object{
        
        private val migration_1_2 = object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE contacts ADD COLUMN gender TEXT")
            }

        }
        private var INSTANCE: ConactDatabase? = null

        fun getDatabaseInstance(context: Context): ConactDatabase {
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ConactDatabase::class.java,"ContactDB").addMigrations(migration_1_2).build()
                }

            }
            return INSTANCE!!
        }
    }

}