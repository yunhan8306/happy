package com.example.happy.network.service

import com.example.happy.network.dto.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface CollectionService {
    @GET
    suspend fun searchList(
        @Url url: String
    ): CollectionResponse
}