package com.example.happy.data.datasource

import com.example.happy.network.service.CollectionService
import com.example.happy.network.dto.CollectionResponse
import com.example.happy.presentation.list.CollectionRequestData
import javax.inject.Inject

class CollectionDataSourceImpl @Inject constructor(
    private val collectionService: CollectionService
): CollectionDataSource {
    override suspend fun searchList(request: CollectionRequestData): CollectionResponse {
        val apiKey = "6b49677a67707075373169594f4f53"
        val url = "http://openapi.seoul.go.kr:8088/$apiKey/json/SemaPsgudInfoKorInfo/" +
                "${request.startIndex}" +
                "/${request.endIndex}" +
                "/${request.productCategory}" +
                "/${request.manageYear}" +
                "/${request.productNameKorean}" +
                "/${request.productNameEnglish}"
        return collectionService.searchList(
            url = url
        )
    }
}

