package com.example.iquiitest.api

import com.example.iquiitest.model.RedditResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditService {
    @GET("r/{keyword}/top.json")
    fun searchImages(@Path("keyword") keyword : String) : Call<RedditResponse>
}