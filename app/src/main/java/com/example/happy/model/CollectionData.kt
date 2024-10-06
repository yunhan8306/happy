package com.example.happy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionData(
    val id: Long? = null,
    val category: String = "",
    val manageYear: String = "",
    val titleKor: String = "",
    val titleEng: String = "",
    val standard: String = "",
    val madeYear: String = "",
    val technic: String = "",
    val productDetail: String = "",
    val writerName: String = "",
    val mainUri: String = "",
    val thumbUri: String = "",
): Parcelable

fun List<CollectionData>.getIsLike(collectionData: CollectionData) =
    this.firstOrNull { it.thumbUri == collectionData.thumbUri }?.let { true } ?: false

fun List<CollectionData>.setId(collectionData: CollectionData) =
    this.firstOrNull { it.thumbUri == collectionData.thumbUri }?.let { collectionData.copy(id = it.id) } ?: collectionData
