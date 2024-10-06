package com.example.happy.model

sealed interface SearchListStatus {
    object Fail : SearchListStatus
    object Loading : SearchListStatus
    object EmptyData: SearchListStatus
    object End: SearchListStatus
    data class Success(
        val totalCnt: Int,
        val list: List<CollectionData>
    ): SearchListStatus
}