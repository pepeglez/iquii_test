package com.example.iquiitest.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.iquiitest.MainActivity
import com.example.iquiitest.R
import com.example.iquiitest.model.RedditImage
import com.example.iquiitest.ui.preview.ImagePreviewDialogFragment

class ImageAdapter internal constructor( var context: Context ) :
    RecyclerView.Adapter<ImageAdapter.ItemHolder>() {

    private var adapterList = emptyList<RedditImage>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_grid_item, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val imageItem: RedditImage = adapterList.get(position)

        holder.image.setOnClickListener {
            val dialogFragment = ImagePreviewDialogFragment()
            var activity:MainActivity = context as MainActivity
            dialogFragment.show(activity.supportFragmentManager, "Image preview")
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.iv_image)
    }

    internal fun setList(list: ArrayList<RedditImage>) {
        this.adapterList = list
        notifyDataSetChanged()
    }
}