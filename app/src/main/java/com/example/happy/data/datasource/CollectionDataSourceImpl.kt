package com.example.happy.data.datasource

import com.example.happy.BuildConfig
import com.example.happy.network.service.CollectionService
import com.example.happy.network.dto.CollectionResponse
import com.example.happy.presentation.list.CollectionRequestData
import com.google.gson.internal.GsonBuildConfig
import javax.inject.Inject

class CollectionDataSourceImpl @Inject constructor(
    private val collectionService: CollectionService
): CollectionDataSource {
    override suspend fun searchList(request: CollectionRequestData): CollectionResponse {
        val url = "${BuildConfig.BASE_URL}/${BuildConfig.API_KEY}/json/SemaPsgudInfoKorInfo/" +
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

