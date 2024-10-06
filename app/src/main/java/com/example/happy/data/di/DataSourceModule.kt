package com.example.happy.data.di

import com.example.happy.data.datasource.CollectionDataSource
import com.example.happy.data.datasource.CollectionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun bindsCollectionDataSource(collectionDataSource: CollectionDataSourceImpl): CollectionDataSource
}