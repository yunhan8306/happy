package com.example.happy.database.di

import android.content.Context
import com.example.happy.database.like.dao.LikeDao
import com.myStash.android.data_base.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun providesDataBase(
        @ApplicationContext context: Context
    ): DataBase = DataBase.build(context)
}

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    fun providesLikeDao(
        dataBase: DataBase
    ) : LikeDao = dataBase.LikeDao()
}