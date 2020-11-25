package com.example.iquiitest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.iquiitest.R
import com.example.iquiitest.api.RedditService
import com.example.iquiitest.model.RedditResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var baseUrl = "https://www.reddit.com/"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                    Log.v("---Log", "" + redditResponse.data.dist)

                    for( Children in redditResponse.data.children){
                        Log.v("---Log", Children.data.title)
                    }
                }
            }
            override fun onFailure(call: Call<RedditResponse>, t: Throwable) {
                Log.v("---Log","Error")

            }
        })
    }
}