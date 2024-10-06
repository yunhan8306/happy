package com.example.happy.domain.usecase

import com.example.happy.domain.repository.LikeRepository
import com.example.happy.model.CollectionData
import javax.inject.Inject

class DeleteLikeUseCase @Inject constructor(
    private val likeRepository: LikeRepository
) {
    suspend operator fun invoke(like: CollectionData) =
        likeRepository.delete(like)
}