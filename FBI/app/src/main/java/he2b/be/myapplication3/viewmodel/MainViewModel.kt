package he2b.be.myapplication3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import he2b.be.myapplication3.network.WantedItem
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import he2b.be.myapplication3.network.FBIClient.api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _titles = MutableStateFlow<List<WantedItem>>(emptyList())
    val titles: StateFlow<List<WantedItem>> = _titles


    val errorMessage = mutableStateOf<String?>(null)
    private var currentPage = 1
    var isLoading = false
    private var isLastPage = false

    var lastSelectedOption = "default"
        private set

    init {
        fetchWantedTitles()
    }

    private fun fetchWantedTitles() {
        if (isLoading || isLastPage) return

        viewModelScope.launch {
            isLoading = true
            try {
                val response = api.getWanted(currentPage, "Android App")
                if (response.isSuccessful) {
                    val newItems = response.body()?.items ?: emptyList()
                    _titles.value = _titles.value + newItems
                    currentPage++
                    isLastPage = newItems.isEmpty()
                    errorMessage.value = null
                } else {
                    errorMessage.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching wanted titles", e)
                errorMessage.value = "Network error: No internet"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateFilter(newFilter: String) {
        _titles.value = listOf()
        currentPage = 1
        isLastPage = false
        fetchByCategory(newFilter)
    }

    fun saveLastSelectedOption(option: String) {
        lastSelectedOption = option
    }

    fun fetchByCategory(posterClassification: String?) {
        if (isLoading || isLastPage) return

        viewModelScope.launch {
            isLoading = true
            try {
                val response = posterClassification?.let {
                    api.getPosterClassification(currentPage, it, "Android App")
                }
                if (response != null && response.isSuccessful) {
                    val newItems = response.body()?.items ?: emptyList()
                    _titles.value = _titles.value + newItems
                    currentPage++
                    isLastPage = newItems.isEmpty()
                    errorMessage.value = null
                } else {
                    errorMessage.value = "Error: ${response?.message()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Network error: No internet"
            } finally {
                isLoading = false
            }
        }
    }
}

