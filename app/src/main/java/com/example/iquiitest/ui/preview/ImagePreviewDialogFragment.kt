package com.example.iquiitest.ui.preview

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.iquiitest.MainActivity
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
    private var previewList = emptyList<RedditImage>()
    private var picasso = Picasso.get()
    private var position = 0


    private var redditImage:RedditImage? = null

    constructor (position:Int, application: Application, listImages:List<RedditImage>) : this() {
        this.redditImage = listImages.get(position)
        this.redditImageRepo = RedditImageRepo(application)
        this.previewList = listImages
        this.position = position
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
        imgReddit = view.findViewById(R.id.imgLargePreview)
        tvTittle = view.findViewById(R.id.tv_title)
        tvAuthor = view.findViewById(R.id.tv_author)
        bAddFav = view.findViewById(R.id.b_add_fav)

        settingGestures()
        fillInterface(redditImage!!)
    }

    private fun fillInterface (redditImage: RedditImage){
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

        if (redditImage?.fav!!){
            bAddFav?.visibility = View.GONE
        }else
            bAddFav?.visibility = View.VISIBLE
            bAddFav?.setOnClickListener {
                runBlocking { redditImageRepo?.addToFav(redditImage!!) }
                bAddFav?.visibility = View.GONE
            }
    }

    private fun settingGestures(){

        imgReddit?.listenerDown = {
            dismiss()
        }
        //Swipe LEFT
        imgReddit?.listenerLeft = {
            if (position < previewList.size-1){
                position += 1
                animateLeft()
                Handler().postDelayed({
                    fillInterface(previewList.get(position))
                }, 350)
            }
        }

        //Swipe RIGHT
        imgReddit?.listenerRight = {
            if (position > 0 ){
                position -= 1
                animateRight()
                Handler().postDelayed({
                    fillInterface(previewList.get(position))
                }, 350)
            }
        }

    }

    private fun animateLeft(){
        val outAnimation = AnimatorInflater.loadAnimator(context, R.animator.slide_left) as AnimatorSet
        outAnimation.setTarget(imgReddit)
        val inAnimation = AnimatorInflater.loadAnimator(context, R.animator.slide_left_in) as AnimatorSet
        inAnimation.setTarget(imgReddit)
        val bothAnimatorSet = AnimatorSet()
        bothAnimatorSet.playSequentially(outAnimation, inAnimation)
        bothAnimatorSet.start()
    }

    private fun animateRight(){
        val outAnimation = AnimatorInflater.loadAnimator(context, R.animator.slide_right) as AnimatorSet
        outAnimation.setTarget(imgReddit)
        val inAnimation = AnimatorInflater.loadAnimator(context, R.animator.slide_right_in) as AnimatorSet
        inAnimation.setTarget(imgReddit)
        val bothAnimatorSet = AnimatorSet()
        bothAnimatorSet.playSequentially(outAnimation, inAnimation)
        bothAnimatorSet.start()
    }

}