package com.example.happy.database.di

import com.example.happy.database.like.datasource.LikeDataSource
import com.example.happy.database.like.datasource.LikeDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun bindsCollectionDataSource(likeDataSource: LikeDataSourceImpl): LikeDataSource
}