package com.example.happy.data.di


import com.example.happy.data.repository.CollectionRepositoryImpl
import com.example.happy.domain.repository.CollectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsCollectionRepository(collectionRepository: CollectionRepositoryImpl): CollectionRepository
}