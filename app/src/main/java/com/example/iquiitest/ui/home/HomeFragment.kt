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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iquiitest.R
import com.example.iquiitest.adapters.ImageAdapter
import com.example.iquiitest.api.RedditService
import com.example.iquiitest.model.RedditImage
import com.example.iquiitest.model.RedditResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var baseUrl = "https://www.reddit.com/"

    private var recyclerView: RecyclerView? = null
    private var imageList: ArrayList<RedditImage>? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var imageAdapter: ImageAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        settingInterface(root)

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun settingInterface (root: View){
        recyclerView = root.findViewById(R.id.rv_images)
        gridLayoutManager =
            GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        imageList = ArrayList()
        imageList = fillImageList()
        imageAdapter = context?.let { ImageAdapter(it, imageList!!) }
        recyclerView?.adapter = imageAdapter
    }

    fun fillImageList(): ArrayList<RedditImage>{

        var list: ArrayList<RedditImage> = ArrayList()

        list.add(RedditImage(1,"url","Tittle 1" , "Author 1"))
        list.add(RedditImage(2,"url","Tittle 2" , "Author 2"))
        list.add(RedditImage(3,"url","Tittle 3" , "Author 3"))
        list.add(RedditImage(3,"url","Tittle 3" , "Author 3"))
        list.add(RedditImage(3,"url","Tittle 3" , "Author 3"))

        return list
    }


    fun makeHttpRequest (){
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