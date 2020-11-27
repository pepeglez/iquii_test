package com.example.iquiitest.ui.favorites

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iquiitest.R
import com.example.iquiitest.adapters.ImageAdapter

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel

    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var imageAdapter: ImageAdapter? = null
    private var llNoFav: LinearLayout? = null

    private var appCtx: Application? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_favorites, container, false)

        appCtx = requireActivity()!!.application

        settingInterface(root)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        var searchItem = menu?.findItem(R.id.m_action_search)
        searchItem.setVisible(false)

        super.onCreateOptionsMenu(menu, inflater)
    }

    fun settingInterface (root: View){
        recyclerView = root.findViewById(R.id.rv_favorites)
        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        llNoFav = root.findViewById(R.id.ll_empty_fav)

        imageAdapter = context?.let { ImageAdapter(it, appCtx!!) }

        recyclerView?.adapter = imageAdapter
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        favoritesViewModel.getFavImages()?.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty())
                llNoFav?.visibility = View.VISIBLE
            else
                llNoFav?.visibility = View.INVISIBLE
            it.let { imageAdapter?.setList(it) }
        })
    }

}