package com.demo.app.app.di.database

import android.content.Context
import androidx.room.Room
import com.demo.app.data.database.MyDatabase
import com.demo.app.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MyDatabase {
        return Room.databaseBuilder(appContext,
            MyDatabase::class.java,
            "MyDatabase.db")
            .build()
    }

    @Provides
    fun provideUserDao(database: MyDatabase): UserDao {
        return database.userDao()
    }
}
