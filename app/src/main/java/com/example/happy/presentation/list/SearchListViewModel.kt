package com.example.happy.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happy.common.util.isEnglish
import com.example.happy.common.util.isKorean
import com.example.happy.common.util.safeLaunch
import com.example.happy.domain.usecase.SearchCollectionUseCase
import com.example.happy.model.CollectionData
import com.example.happy.model.FilterData
import com.example.happy.model.SearchListStatus
import com.example.happy.model.getInitCategory
import com.example.happy.model.getInitSorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor(
    private val searchListUseCase: SearchCollectionUseCase
) : ViewModel() {

    private val _searchListStatus = MutableStateFlow<SearchListStatus>(SearchListStatus.Loading)
    val searchListStatus = _searchListStatus.asStateFlow()
//    val successStatus get() = _searchListStatus.value as? SearchListStatus.Success

    private val searchList = mutableListOf<CollectionData>()

    private val _totalFilterData = MutableStateFlow(Pair(getInitCategory(), getInitSorting()))
    val totalFilterData = _totalFilterData.asStateFlow()

    init {
        getSearchList()
    }

    fun getSearchList(
        text: String = "",
        manageYear: String = "",
    ) {
        viewModelScope.safeLaunch {
            if(isDataEnd()) {
                _searchListStatus.value = SearchListStatus.End
            } else {
                val request = CollectionRequestData(
                    startIndex = searchList.size,
                    endIndex = searchList.size + 100,
                    productCategory = _totalFilterData.value.first.getFilterName(),
                    manageYear = manageYear,
                    productNameKorean = if(text.isKorean()) text else CollectionRequestData.EMPTY,
                    productNameEnglish = if(text.isEnglish()) text else CollectionRequestData.EMPTY,
                )
                _searchListStatus.value = SearchListStatus.Loading
                val response = searchListUseCase.invoke(request)

                when(response) {
                    is SearchListStatus.Success -> {
                        searchList.addAll(response.list)
                        _searchListStatus.value = SearchListStatus.Success(
                            totalCnt = searchList.size,
                            list = searchList.toList(),
                            category = _totalFilterData.value.first,
                            sorting = _totalFilterData.value.second
                        )
                    }
                    else -> {
                        _searchListStatus.value = response
                    }
                }
            }
        }
    }

    fun refresh() {
        searchList.clear()
    }

    fun resetFilter() {
        _totalFilterData.value = Pair(getInitCategory(), getInitSorting())
        getSearchList()
    }

    fun setFilter(filterType: String, data: FilterData?) {
        when(filterType) {
            "category" -> {
                _totalFilterData.value = Pair(data ?: getInitCategory(), getInitSorting())
                searchList.clear()
                getSearchList()
            }
            "sorting" -> {
                _totalFilterData.value = Pair(_totalFilterData.value.first, data ?: getInitSorting())
                searchList.reverse()
                _searchListStatus.value = SearchListStatus.Success(
                    totalCnt = searchList.size,
                    list = searchList.toList(),
                    category = _totalFilterData.value.first,
                    sorting = _totalFilterData.value.second
                )
            }
            else -> {
                _totalFilterData.value = Pair(getInitCategory(), getInitSorting())
                searchList.clear()
                getSearchList()
            }
        }
    }

    private fun isDataEnd(): Boolean =
        searchList.size < ((searchListStatus.value as? SearchListStatus.Success)?.totalCnt ?: 0) && searchList.isNotEmpty()
}
