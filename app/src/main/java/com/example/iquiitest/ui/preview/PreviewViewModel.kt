package com.example.iquiitest.ui.preview

import android.app.Application
import androidx.lifecycle.*
import com.example.iquiitest.data.RedditImage
import com.example.iquiitest.repo.RedditImageRepo
import kotlinx.coroutines.launch

class PreviewViewModel(application: Application, imageList: List<RedditImage>?) : AndroidViewModel(application) {

    var _imagesList : LiveData<List<RedditImage>>? = null

    val redditImageRepo = RedditImageRepo(application)

    init {
    }

    fun getImages() : LiveData<List<RedditImage>>? {

        viewModelScope.launch {
            _imagesList = redditImageRepo.getFavImages()
        }
        return _imagesList
    }
}