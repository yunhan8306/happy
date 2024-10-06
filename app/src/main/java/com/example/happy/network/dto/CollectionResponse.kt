package com.example.happy.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionResponse(
    @SerialName("SemaPsgudInfoKorInfo") val collectionResponseData: CollectionResponseData? = null
)

@Serializable
data class CollectionResponseData(
    @SerialName("list_total_count") val totalCount: Int? = 0,
    @SerialName("RESULT") val resultCode: CollectionResponseResult?,
    @SerialName("row") val collectionList: List<CollectionEntity>?,
)

@Serializable
data class CollectionResponseResult(
    @SerialName("CODE") val code: String = "",
    @SerialName("MESSAGE") val message: String = "",
)

@Serializable
data class CollectionEntity(
    @SerialName("prdct_cl_nm") val category: String? = null,
    @SerialName("manage_no_year") val manageYear: String? = null,
    @SerialName("prdct_nm_korean") val titleKor: String? = null,
    @SerialName("prdct_nm_eng") val titleEng: String? = null,
    @SerialName("prdct_stndrd") val standard: String? = null,
    @SerialName("mnfct_year") val madeYear: String? = null,
    @SerialName("matrl_technic") val technic: String? = null,
    @SerialName("prdct_detail") val productDetail: String? = null,
    @SerialName("writr_nm") val writerName: String? = null,
    @SerialName("main_image") val mainUri: String? = null,
    @SerialName("thumb_image") val thumbUri: String? = null,
)