package com.example.happy.database.like.repository

import com.example.happy.database.like.datasource.LikeDataSource
import com.example.happy.database.like.entity.CollectionRoomEntity.Companion.toCollectionData
import com.example.happy.database.like.entity.CollectionRoomEntity.Companion.toCollectionRoomEntity
import com.example.happy.domain.repository.LikeRepository
import com.example.happy.model.CollectionData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val likeDataSource: LikeDataSource
): LikeRepository {
    override suspend fun insert(like: CollectionData): Long =
        likeDataSource.insert(like.toCollectionRoomEntity())

    override suspend fun delete(like: CollectionData): Int =
        likeDataSource.delete(like.toCollectionRoomEntity())

    override fun selectAll(): Flow<List<CollectionData>> =
        likeDataSource.selectAll().map { it.map { it.toCollectionData() } }
}