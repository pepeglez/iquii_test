package com.example.iquiitest.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.iquiitest.api.RedditService
import com.example.iquiitest.data.DatabaseService
import com.example.iquiitest.data.RedditImage
import com.example.iquiitest.data.RedditResponse
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditImageRepo (application: Application) {

    var baseUrl = "https://www.reddit.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val remoteRepo = retrofit.create(RedditService::class.java)

    val localRepo = DatabaseService.getDatabase(application).redditImageDAO()


    suspend fun getImages (keyword: String) : MutableLiveData<List<RedditImage>> {

        return getRemoteImages (keyword)
    }


    private fun getRemoteImages (keyword:String) : MutableLiveData<List<RedditImage>> {

        val ldList = MutableLiveData<List<RedditImage>>()

        val call = remoteRepo.searchImages(keyword)
        call.enqueue(object : Callback<RedditResponse> {
            override fun onResponse(call: Call<RedditResponse>, response: Response<RedditResponse>) {
                if (response.code() == 200) {

                    val redditResponse = response.body()!!
                    var list: ArrayList<RedditImage> = ArrayList()

                    runBlocking { deleteImages(keyword) }

                    for( Children in redditResponse.data.children){
                        var url:String? = null
                        if (Children.data.preview!=null){
                            url = Children.data.preview.images[0].source.url
                            url = url.replace("amp;", "")
                        } else null

                        var modelImage = RedditImage(
                            0,
                            Children.data.title,
                            Children.data.author,
                            url,
                            if (Children.data.thumbnail!=null) Children.data.thumbnail else null,
                            keyword
                        )
                        list.add(modelImage)
                        runBlocking { insert(modelImage, keyword) }
                    }
                    ldList.value = list
                }else{
                    var list: ArrayList<RedditImage> = ArrayList()
                    ldList.value = list
                }
            }
            override fun onFailure(call: Call<RedditResponse>, t: Throwable) {
                runBlocking {ldList.value = localRepo.getImagesByKeyword(keyword)}
            }
        })
        return ldList
    }


    suspend fun getDBImages(keyword: String): MutableLiveData<List<RedditImage>> {

        val ldList = MutableLiveData<List<RedditImage>>()
        var list: List<RedditImage>? = ArrayList()
        ldList.value = localRepo.getImagesByKeyword(keyword)
        return ldList
    }

    suspend fun insert(image: RedditImage, keyword: String) {
        localRepo.insert(image)
    }

    suspend fun deleteImages( keyword: String) {
        localRepo.deleteByKeyword(keyword)
    }

}