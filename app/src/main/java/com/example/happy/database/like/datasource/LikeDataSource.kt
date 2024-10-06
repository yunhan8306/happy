package com.example.happy.database.like.datasource

import com.example.happy.database.like.entity.CollectionRoomEntity
import kotlinx.coroutines.flow.Flow

interface LikeDataSource {
    suspend fun insert(entity: CollectionRoomEntity): Long
    suspend fun delete(entity: CollectionRoomEntity): Int
    fun selectAll() : Flow<List<CollectionRoomEntity>>
}