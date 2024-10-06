package com.example.happy.domain.repository

import com.example.happy.model.CollectionData
import kotlinx.coroutines.flow.Flow

interface LikeRepository {
    suspend fun insert(like: CollectionData): Long
    suspend fun delete(like: CollectionData): Int
    fun selectAll() : Flow<List<CollectionData>>
}