package com.example.iquiitest.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.iquiitest.data.RedditImage
import com.example.iquiitest.repo.RedditImageRepo
import kotlinx.coroutines.launch


class HomeViewModel (application: Application) : AndroidViewModel(application) {

    var _imagesList : MutableLiveData<List<RedditImage>>? = null

    val redditImageRepo = RedditImageRepo(application)

    fun getImages(keyword: String) : MutableLiveData<List<RedditImage>>? {

        viewModelScope.launch {
            _imagesList = redditImageRepo.getImages(keyword)
        }
        return _imagesList
    }

}