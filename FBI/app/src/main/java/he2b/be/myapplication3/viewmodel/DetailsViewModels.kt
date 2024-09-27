package he2b.be.myapplication3.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import he2b.be.myapplication3.network.FBIClient
import he2b.be.myapplication3.network.WantedItem
import he2b.be.myapplication3.network.isInternetAvailable
import kotlinx.coroutines.launch

class DetailsViewModels: ViewModel() {
    val details = mutableStateOf<WantedItem?>(null)
    private val errorMessage = mutableStateOf<String?>(null)

    fun fetchDetailsByUID(uid: String?, context: Context) {
        viewModelScope.launch {
            if (!isInternetAvailable(context)) {
                details.value = null
                return@launch
            }
            uid?.let {
                try {
                    val response = FBIClient.api.getDetailsByUID(it, "Android App")
                    if (response.isSuccessful) {
                        details.value = response.body()
                        errorMessage.value = null
                    } else {
                        errorMessage.value = "Error: ${response.message()}"
                    }
                } catch (e: Exception) {
                    Log.e("MainViewModel", "Error fetching details by UID", e)
                }
            }
        }
    }
}

class DetailsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModels::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModels() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
