package com.example.iquiitest.ui.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iquiitest.MainActivity
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


    private var recyclerView: RecyclerView? = null
    private var imageList: ArrayList<RedditImage>? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var imageAdapter: ImageAdapter? = null

    private var parentActivity:MainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        parentActivity = activity as MainActivity

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        settingInterface(root)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        val searchItem = menu?.findItem(R.id.m_action_search)
        if (searchItem!=null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    fetchRedditImages(query!!)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    fetchRedditImages(newText!!)
                    return true
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun settingInterface (root: View){
        recyclerView = root.findViewById(R.id.rv_images)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)

        imageAdapter = context?.let { ImageAdapter(it) }

        recyclerView?.adapter = imageAdapter
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        fetchRedditImages("italia")
    }

    fun fetchRedditImages (keyword:String) {
        homeViewModel.getImages(keyword)?.observe(viewLifecycleOwner, Observer {images ->
            images.let { imageAdapter?.setList(it) }
        })
    }

}