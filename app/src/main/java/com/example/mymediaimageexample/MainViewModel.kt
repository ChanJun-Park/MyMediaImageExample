package com.example.mymediaimageexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
	private val mediaImageLoader: MediaImageLoader
): ViewModel() {

	private val _images = MutableStateFlow<List<MediaImage>>(emptyList())
	val images: StateFlow<List<MediaImage>> = _images

	init {
		getImages()
	}

	fun getImages() {
		viewModelScope.launch {
			_images.value = mediaImageLoader.getMediaImages()
		}
	}

	@Suppress("UNCHECKED_CAST")
	class Factory(private val mediaImageLoader: MediaImageLoader): ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
				MainViewModel(mediaImageLoader) as T
			} else {
				throw IllegalStateException("Not supported ViewModel class")
			}
		}
	}
}