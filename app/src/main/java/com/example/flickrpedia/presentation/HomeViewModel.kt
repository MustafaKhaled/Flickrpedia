package com.example.flickrpedia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrpedia.data.model.Images
import com.example.flickrpedia.domain.repo.ImagesRepo
import com.example.flickrpedia.misc.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val imagesRepo: ImagesRepo) : ViewModel() {
    private val _uiState = MutableStateFlow<ImagesUiState>(ImagesUiState.Loading)
    val uiState = _uiState.asStateFlow()
    fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ImagesUiState.Loading
            try {
                val result = imagesRepo.getImages()
                _uiState.value = ImagesUiState.Success(result)
            } catch (exception: Exception) {
                _uiState.value = ImagesUiState.Error(exception.message.toString())
            }
        }
    }

    sealed class ImagesUiState {
        object Loading : ImagesUiState()
        data class Success(val images: Images) : ImagesUiState()
        data class Error(val message: String) : ImagesUiState()
    }
}