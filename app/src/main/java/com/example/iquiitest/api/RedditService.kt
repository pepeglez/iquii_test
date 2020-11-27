package com.example.iquiitest.api

import com.example.iquiitest.data.RedditResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditService {
    @GET("r/{keyword}/top.json")
    fun searchImages(@Path("keyword") keyword : String) : Call<RedditResponse>
}