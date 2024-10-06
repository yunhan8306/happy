package com.example.happy.database.like.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.happy.database.like.entity.CollectionRoomEntity
import com.myStash.android.data_base.BaseDao
import kotlinx.coroutines.flow.Flow


@Dao
interface LikeDao : BaseDao<CollectionRoomEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: CollectionRoomEntity): Long

    @Delete
    override suspend fun delete(entity: CollectionRoomEntity): Int

    @Query("SELECT * FROM like")
    fun selectAll() : Flow<List<CollectionRoomEntity>>
}