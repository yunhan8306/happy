package com.example.happy.database.like.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.happy.model.CollectionData

@Entity(tableName = "like")
data class CollectionRoomEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long? = null,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "manageYear")
    val manageYear: String,

    @ColumnInfo(name = "titleKor")
    val titleKor: String,

    @ColumnInfo(name = "titleEng")
    val titleEng: String,

    @ColumnInfo(name = "standard")
    val standard: String,

    @ColumnInfo(name = "madeYear")
    val madeYear: String,

    @ColumnInfo(name = "technic")
    val technic: String,

    @ColumnInfo(name = "productDetail")
    val productDetail: String,

    @ColumnInfo(name = "writerName")
    val writerName: String,

    @ColumnInfo(name = "mainUri")
    val mainUri: String,

    @ColumnInfo(name = "thumbUri")
    val thumbUri: String,
) {
    companion object {
        fun CollectionRoomEntity.toCollectionData() = CollectionData(
            id = id,
            category = category,
            manageYear = manageYear,
            titleKor = titleKor,
            titleEng = titleEng,
            standard = standard,
            madeYear = madeYear,
            technic = technic,
            productDetail = productDetail,
            writerName = writerName,
            mainUri = mainUri,
            thumbUri = thumbUri,
        )

        fun CollectionData.toCollectionRoomEntity() = CollectionRoomEntity(
            id = id,
            category = category,
            manageYear = manageYear,
            titleKor = titleKor,
            titleEng = titleEng,
            standard = standard,
            madeYear = madeYear,
            technic = technic,
            productDetail = productDetail,
            writerName = writerName,
            mainUri = mainUri,
            thumbUri = thumbUri,
        )
    }
}