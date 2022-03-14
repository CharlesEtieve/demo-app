package com.demo.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.app.data.database.dao.UserDao
import com.demo.app.data.models.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

