package com.example.iquiitest.ui.home

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iquiitest.InfoDialogFragment
import com.example.iquiitest.MainActivity
import com.example.iquiitest.R
import com.example.iquiitest.adapters.ImageAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var imageAdapter: ImageAdapter? = null
    private var llWelcome: LinearLayout? = null
    private var llNoResults: LinearLayout? = null
    private var bSearch: Button? = null
    private var searchItem: MenuItem? = null
    private var searchView: SearchView? = null
    private var appCtx: Application? = null

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

        appCtx = requireActivity()!!.application

        settingInterface(root)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        searchItem = menu?.findItem(R.id.m_action_search)
        searchItem?.expandActionView()
        if (searchItem!=null){
            searchView = searchItem?.actionView as SearchView
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    fetchRedditImages(query!!)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    llWelcome?.visibility = View.GONE

                    fetchRedditImages(newText!!)
                    return true
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.m_action_info -> {

                InfoDialogFragment().show(activity?.supportFragmentManager!!, "Info dialog")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

            fun settingInterface (root: View){
        recyclerView = root.findViewById(R.id.rv_images)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        llWelcome = root.findViewById(R.id.ll_welcome)
        llNoResults = root.findViewById(R.id.ll_empty)
        bSearch = root.findViewById(R.id.b_search)

        imageAdapter = context?.let { ImageAdapter(it , appCtx!!)  }

        recyclerView?.adapter = imageAdapter
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        bSearch?.setOnClickListener {
            searchView?.setIconifiedByDefault(false)
            searchView?.requestFocus()
            bSearch?.showKeyboard()
        }
    }

    fun fetchRedditImages (keyword:String) {
        homeViewModel.getImages(keyword)?.observe(viewLifecycleOwner, Observer {images ->
            if (images.isNullOrEmpty())
                llNoResults?.visibility = View.VISIBLE
            else
                llNoResults?.visibility = View.INVISIBLE
            images.let { imageAdapter?.setList(it) }
        })
    }

    private fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

}