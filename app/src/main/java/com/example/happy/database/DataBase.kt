package com.myStash.android.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.happy.database.like.dao.LikeDao
import com.example.happy.database.like.entity.CollectionRoomEntity

@Database(
    entities = [
        CollectionRoomEntity::class
    ],
    version = 1
)
@TypeConverters
abstract class DataBase : RoomDatabase() {
    abstract fun LikeDao() : LikeDao

    companion object {
        private const val DATA_BASE_NAME = "happyDataBase"

        fun build(context: Context) = Room.databaseBuilder(
            context = context,
            klass = DataBase::class.java,
            name = DATA_BASE_NAME
        )
            .fallbackToDestructiveMigration().build()
    }
}