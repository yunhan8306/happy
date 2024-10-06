package com.example.happy.data.datasource

import com.example.happy.network.dto.CollectionResponse
import com.example.happy.presentation.list.CollectionRequestData

interface CollectionDataSource {
    suspend fun searchList(request: CollectionRequestData): CollectionResponse
}