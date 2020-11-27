package com.example.iquiitest.ui.favorites

import android.app.Application
import androidx.lifecycle.*
import com.example.iquiitest.data.RedditImage
import com.example.iquiitest.repo.RedditImageRepo
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    var _imagesList : LiveData<List<RedditImage>>? = null

    val redditImageRepo = RedditImageRepo(application)

    fun getFavImages() : LiveData<List<RedditImage>>? {

        viewModelScope.launch {
            _imagesList = redditImageRepo.getFavImages()
        }
        return _imagesList
    }
}