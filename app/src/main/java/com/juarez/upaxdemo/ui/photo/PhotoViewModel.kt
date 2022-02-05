package com.juarez.upaxdemo.ui.photo

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.upaxdemo.data.models.Photo
import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import com.juarez.upaxdemo.data.repositories.FirebaseResult
import com.juarez.upaxdemo.ui.photo.photos.DeletePhotoState
import com.juarez.upaxdemo.ui.photo.photos.GetPhotosState
import com.juarez.upaxdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val repository: FirebaseRepository) : ViewModel() {

    private val _savePhotoState = MutableStateFlow<SavePhotoState>(SavePhotoState.Loading(false))
    val savePhotoState: StateFlow<SavePhotoState> = _savePhotoState
    private val _deletePhotoState =
        MutableStateFlow<DeletePhotoState>(DeletePhotoState.Empty)
    val deletePhotoState: StateFlow<DeletePhotoState> = _deletePhotoState
    private val _photosState = MutableStateFlow<GetPhotosState>(GetPhotosState.Empty)
    val photosState: StateFlow<GetPhotosState> = _photosState

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

    fun getImages() {
        repository.getImages().onEach {
            when (it) {
                is FirebaseResult.Loading -> {
                    _photosState.value = GetPhotosState.Loading(it.isLoading)
                }
                is FirebaseResult.Success -> _photosState.value = GetPhotosState.Success(it.data)
            }
        }.catch { e ->
            _photosState.value = GetPhotosState.Loading(false)
            _photosState.value = GetPhotosState.Error(e.localizedMessage ?: "Unexpected Exception")
        }.launchIn(viewModelScope)
    }

    fun deletePhoto(pos: Int, oldList: List<Photo>) {
        val photo = oldList[pos]
        Log.d("photo", photo.toString())
        repository.deletePhoto(photo.filename).onEach {
            when (it) {
                is FirebaseResult.Loading -> {
                    _deletePhotoState.value = DeletePhotoState.Loading(it.isLoading)
                }
                is FirebaseResult.Success -> {
                    val newList = oldList.filterIndexed { i, _ -> i != pos }
                    _photosState.value = GetPhotosState.Success(newList)
                    _deletePhotoState.value = DeletePhotoState.Success
                }
            }
        }.launchIn(viewModelScope)
    }
}