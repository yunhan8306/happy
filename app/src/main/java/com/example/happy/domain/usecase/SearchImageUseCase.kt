package com.example.happy.domain.usecase

import com.example.happy.domain.repository.CollectionRepository
import com.example.happy.network.dto.CollectionResponse
import com.example.happy.presentation.list.CollectionRequestData
import kotlinx.coroutines.flow.first
import okhttp3.RequestBody
import javax.inject.Inject

class SearchCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke(request: CollectionRequestData) =
        collectionRepository.searchList(request)

}