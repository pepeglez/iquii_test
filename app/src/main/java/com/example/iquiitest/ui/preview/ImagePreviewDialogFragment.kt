package com.example.iquiitest.ui.preview

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.iquiitest.R
import com.example.iquiitest.model.RedditImage
import com.squareup.picasso.Picasso

class ImagePreviewDialogFragment () : DialogFragment( ) {

    private var imgReddit: ImageView? = null
    private var tvTittle: TextView? = null
    private var tvAuthor: TextView? = null
    private var redditImage:RedditImage? = null

    constructor (redditImage:RedditImage) : this() {
        this.redditImage = redditImage
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

    fun settingInterface (view:View){
        var picasso = Picasso.get()
        imgReddit = view.findViewById(R.id.imgLargePreview)
        tvTittle = view.findViewById(R.id.tv_title)
        tvAuthor = view.findViewById(R.id.tv_author)

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
    }


}