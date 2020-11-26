package com.example.iquiitest.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iquiitest.api.RedditService
import com.example.iquiitest.model.RedditImage
import com.example.iquiitest.model.RedditResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {

    var baseUrl = "https://www.reddit.com/"

    private var _redditImages: MutableLiveData<ArrayList<RedditImage>> = MutableLiveData()

    init {
        makeHttpRequest()
    }

    internal var listImages:MutableLiveData<ArrayList<RedditImage>>
        get() { return _redditImages}
        set(value) {_redditImages = value}

    fun dame() : MutableLiveData<ArrayList<RedditImage>>{

        return _redditImages
    }

    private fun makeHttpRequest (){
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RedditService::class.java)

        val call = service.searchImages("italia")
        call.enqueue(object : Callback<RedditResponse> {
            override fun onResponse(call: Call<RedditResponse>, response: Response<RedditResponse>) {
                if (response.code() == 200) {

                    val redditResponse = response.body()!!
                    var list: ArrayList<RedditImage> = ArrayList()
                    var id:Int = 0


                    for( Children in redditResponse.data.children){
                        var url:String? = null

                        if (Children.data.preview!=null){
                            url = Children.data.preview.images[0].source.url
                            url = url.replace("amp;", "")
                        } else null

                        list.add(RedditImage(
                            id.inc() ,
                            if (Children.data.thumbnail!=null) Children.data.thumbnail else null,
                            url,
                            Children.data.title,
                            Children.data.author))
                    }

                    listImages.value = list
                }
            }
            override fun onFailure(call: Call<RedditResponse>, t: Throwable) {
                Log.v("---Log","Error")
            }
        })
    }
}