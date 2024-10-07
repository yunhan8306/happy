package com.example.happy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterData(
    val no: Int,
    val name: String,
    val isSelect: Boolean = false
): Parcelable {
    companion object {
        const val EMPTY = " "
    }

    fun getFilterName() = if(name == "전체") EMPTY else this.name
}

fun FilterData?.getCategoryList(): List<FilterData> {
    return listOf("전체", "회화", "한국화", "드로잉&판화", "조각", "뉴미디어", "사진", "설치", "디자인").mapIndexed { index, name ->
        FilterData(
            no = index,
            name = name,
            isSelect = this?.name == name
        )
    }
}

fun FilterData?.getSortingList(): List<FilterData> {
    return listOf("오름차순", "내림차순").mapIndexed { index, name ->
        FilterData(
            no = index,
            name = name,
            isSelect = this?.name == name
        )
    }
}

fun getInitCategory() = FilterData(0, "전체", true)

fun getInitSorting() = FilterData(0, "오름차순", true)