package com.example.happy.domain.repository

import com.example.happy.model.SearchListStatus
import com.example.happy.presentation.list.CollectionRequestData

interface CollectionRepository {
    suspend fun searchList(request: CollectionRequestData): SearchListStatus
}