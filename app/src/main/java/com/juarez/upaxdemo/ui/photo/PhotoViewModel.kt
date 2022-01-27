package com.juarez.upaxdemo.ui.photo

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.upaxdemo.data.repositories.FirebaseRepository
import com.juarez.upaxdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val repository: FirebaseRepository) : ViewModel() {

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess
    private var _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    fun savePhoto(filename: String, uri: Uri, extension: String?) {
        val imageFileName: String
        val dateFormatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH)
        val dateFileName = dateFormatter.format(Date())

        if (filename.isNotEmpty()) {
            imageFileName = "${dateFileName}_${filename}.${extension ?: "png"}"
        } else {
            imageFileName = "${dateFileName}.${extension ?: "png"}"
        }
        repository.uploadImage(uri, imageFileName).onEach { result ->
            when (result) {
                is Resource.Loading -> _loading.value = result.isLoading
                is Resource.Success -> result.data.also { _isSuccess.value = it }
                is Resource.Error -> Unit
            }
        }.launchIn(viewModelScope)
    }
}