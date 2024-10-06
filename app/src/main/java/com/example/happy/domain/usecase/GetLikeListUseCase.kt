package com.example.happy.domain.usecase

import com.example.happy.domain.repository.LikeRepository
import javax.inject.Inject

class GetLikeListUseCase @Inject constructor(
    private val likeRepository: LikeRepository
) {
    val likeList = likeRepository.selectAll()
}