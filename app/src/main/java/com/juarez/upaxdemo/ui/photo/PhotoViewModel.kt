package com.juarez.upaxdemo.ui.photo

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import com.juarez.upaxdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val repository: FirebaseRepository) : ViewModel() {

    private val _savePhotoState = MutableStateFlow<SavePhotoState>(SavePhotoState.Loading(false))
    val savePhotoState: StateFlow<SavePhotoState> = _savePhotoState

    fun savePhoto(filename: String, uri: Uri, extension: String?) {
        val dateFormatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH)
        val dateFileName = dateFormatter.format(Date())

        val imageFileName = if (filename.isNotEmpty()) {
            "${dateFileName}_${filename}.${extension ?: "png"}"
        } else {
            "${dateFileName}.${extension ?: "png"}"
        }
        repository.uploadImage(uri, imageFileName).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _savePhotoState.value = SavePhotoState.Loading(result.isLoading)
                }
                is Resource.Success -> _savePhotoState.value = SavePhotoState.Success
                is Resource.Error -> Unit
            }
        }.launchIn(viewModelScope)
    }
}