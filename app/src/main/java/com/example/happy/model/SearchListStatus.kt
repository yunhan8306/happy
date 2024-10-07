package com.example.happy.model

sealed interface SearchListStatus {
    object Fail : SearchListStatus
    object Loading : SearchListStatus
    object EmptyData: SearchListStatus
    object End: SearchListStatus
    data class Success(
        val totalCnt: Int,
        val list: List<CollectionData>,
        val category: FilterData? = null,
        val sorting: FilterData = FilterData(0, "오름차순, true")
    ): SearchListStatus
}