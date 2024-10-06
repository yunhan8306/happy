package com.example.happy.data.repository

import com.example.happy.data.datasource.CollectionDataSource
import com.example.happy.data.model.toCollectionData
import com.example.happy.domain.repository.CollectionRepository
import com.example.happy.model.SearchListStatus
import com.example.happy.presentation.list.CollectionRequestData
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionDataSource: CollectionDataSource
): CollectionRepository {
    override suspend fun searchList(request: CollectionRequestData): SearchListStatus {

        val response = collectionDataSource.searchList(request).collectionResponseData

        return when(response?.resultCode?.code) {
            "INFO-000" -> {
                // 성공
                SearchListStatus.Success(
                    totalCnt = response.totalCount ?: 0,
                    list = response.collectionList?.map { it.toCollectionData() } ?: emptyList()
                )
            }
            "INFO-200" -> {
                // 데이터 없음
                SearchListStatus.EmptyData
            }
            else -> {
                SearchListStatus.Fail
            }
        }
    }
}