package com.example.happy.data.di


import com.example.happy.data.repository.CollectionRepositoryImpl
import com.example.happy.database.like.repository.LikeRepositoryImpl
import com.example.happy.domain.repository.CollectionRepository
import com.example.happy.domain.repository.LikeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsCollectionRepository(collectionRepository: CollectionRepositoryImpl): CollectionRepository

    @Binds
    fun bindsLikeRepository(likeRepository: LikeRepositoryImpl): LikeRepository
}