package com.example.happy.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happy.domain.usecase.SearchCollectionUseCase
import com.example.happy.model.CollectionData
import com.example.happy.model.SearchListStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor(
    private val searchListUseCase: SearchCollectionUseCase
) : ViewModel() {

    private val _searchListStatus = MutableStateFlow<SearchListStatus>(SearchListStatus.Loading)
    val searchListStatus = _searchListStatus.asStateFlow()

    private val searchList = mutableListOf<CollectionData>()

    init {
        getSearchList()
    }

    fun getSearchList() {
        viewModelScope.launch {
            val request = CollectionRequestData(searchList.size, searchList.size + 10)
            _searchListStatus.value = SearchListStatus.Loading
            val response = searchListUseCase.invoke(request)

            when(response) {
                is SearchListStatus.Success -> {
                    searchList.addAll(response.list)
                    _searchListStatus.value = SearchListStatus.Success(
                        totalCnt = searchList.size,
                        list = searchList.toList()
                    )
                }
                else -> {
                    _searchListStatus.value = response
                }
            }
        }
    }
}
