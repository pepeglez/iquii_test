package com.example.iquiitest.ui.preview

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.iquiitest.R
import com.example.iquiitest.data.RedditImage
import com.example.iquiitest.repo.RedditImageRepo
import com.example.iquiitest.utils.ZoomClass
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking

class ImagePreviewDialogFragment () : DialogFragment() {

    private var imgReddit: ZoomClass? = null
    private var tvTittle: TextView? = null
    private var tvAuthor: TextView? = null
    private var bAddFav: Button? = null
    private var redditImageRepo: RedditImageRepo? = null

    private var redditImage:RedditImage? = null

    constructor (redditImage:RedditImage, application: Application) : this() {
        this.redditImage = redditImage
        this.redditImageRepo = RedditImageRepo(application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        isCancelable = true
        var view = inflater.inflate(R.layout.fragment_image_preview, container, false)
        settingInterface(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismiss()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun settingInterface (view:View){
        var picasso = Picasso.get()
        imgReddit = view.findViewById(R.id.imgLargePreview)
        tvTittle = view.findViewById(R.id.tv_title)
        tvAuthor = view.findViewById(R.id.tv_author)
        bAddFav = view.findViewById(R.id.b_add_fav)

        tvTittle?.text = redditImage?.tittle
        tvAuthor?.text = redditImage?.author

        if (redditImage?.url!=null){
            picasso.load(redditImage?.url)
                .placeholder(R.drawable.iquii_ins)
                .into( imgReddit)
        }else {
            picasso.load(R.drawable.iquii_ins)
                .into( imgReddit )
        }

        imgReddit?.listenerDown = {
            dismiss()
        }

        if (redditImage?.fav!!){
            bAddFav?.visibility = View.GONE
        }else
            bAddFav?.setOnClickListener {
                runBlocking { redditImageRepo?.addToFav(redditImage!!) }
                bAddFav?.visibility = View.GONE
            }
    }

}