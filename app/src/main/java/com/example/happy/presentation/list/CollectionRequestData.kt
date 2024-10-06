package com.example.happy.presentation.list

data class CollectionRequestData(
    val startIndex: Int,  // 요청 시작 위치
    val endIndex: Int,  // 요청 종료 위치
    val productCategory: String = EMPTY,  // 선택 부문 정보
    val manageYear: String = EMPTY,  // 선택 수집 연도
    val productNameKorean: String = EMPTY,  // 선택 국문 작품명
    val productNameEnglish: String = EMPTY  // 선택 영문 작품명
) {
    companion object {
        const val EMPTY = " "
    }
}