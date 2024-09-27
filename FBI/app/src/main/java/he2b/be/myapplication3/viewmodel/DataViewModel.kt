package he2b.be.myapplication3.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import he2b.be.myapplication3.wantedDataBase.WantedItemEntity
import he2b.be.myapplication3.network.WantedItem
import he2b.be.myapplication3.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel : ViewModel() {
    val allItems = mutableStateOf<List<WantedItemEntity>>(listOf())

    init {
        loadAllItems()
    }

    private fun loadAllItems() {
        viewModelScope.launch {
            allItems.value = withContext(Dispatchers.IO) {
                Repository.getAllWantedItemsFromDatabase()
            }
        }
    }

    fun likeItem(item: WantedItem) {
        viewModelScope.launch {
            val entity = WantedItemEntity(
                uid = item.uid,
                title = item.title,
                name = item.name ?: "Unknown Name",
                age = item.age ?: "Unknown Age",
                description= item.description?: "Unknown Age",
                images= item.images.firstOrNull()?.original ?: ""
            )
            insertWantedItemInDatabase(entity)
            loadAllItems()
        }
    }

    private suspend fun insertWantedItemInDatabase(item: WantedItemEntity) {
        withContext(Dispatchers.IO) {
            Repository.insertWantedItemInDatabase(item)
        }
    }

    fun deleteItem(item: WantedItemEntity) {
        viewModelScope.launch {
            deleteWantedItemFromDatabase(item)
            loadAllItems()
        }
    }

    private suspend fun deleteWantedItemFromDatabase(item: WantedItemEntity) {
        withContext(Dispatchers.IO) {
            Repository.deleteWantedItemFromDatabase(item)
        }
    }
}

class DataViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

