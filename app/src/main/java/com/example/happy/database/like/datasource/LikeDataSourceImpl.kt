package com.example.happy.database.like.datasource

import com.example.happy.database.like.entity.CollectionRoomEntity
import com.example.happy.database.like.dao.LikeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikeDataSourceImpl @Inject constructor(
    private val likeDao: LikeDao
) : LikeDataSource {
    override suspend fun insert(entity: CollectionRoomEntity): Long =
        likeDao.insert(entity)

    override suspend fun delete(entity: CollectionRoomEntity): Int =
        likeDao.delete(entity)

    override fun selectAll(): Flow<List<CollectionRoomEntity>> =
        likeDao.selectAll()
}