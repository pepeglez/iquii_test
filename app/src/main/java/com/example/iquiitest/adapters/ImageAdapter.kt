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
import com.example.iquiitest.R
import com.example.iquiitest.model.RedditImage

class ImageAdapter (var context: Context, var arrayList: ArrayList<RedditImage>) :
    RecyclerView.Adapter<ImageAdapter.ItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_grid_item, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val imageItem: RedditImage = arrayList.get(position)

        holder.image.setOnClickListener {
            Log.v("---Log", "" + imageItem.tittle)

        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.iv_image)

    }
}