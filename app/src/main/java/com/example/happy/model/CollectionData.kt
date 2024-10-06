package com.example.happy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionData(
    val category: String,
    val manageYear: String,
    val titleKor: String,
    val titleEng: String,
    val standard: String,
    val madeYear: String,
    val technic: String,
    val productDetail: String,
    val writerName: String,
    val mainUri: String,
    val thumbUri: String,
): Parcelable