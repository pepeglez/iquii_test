package com.example.iquiitest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class InfoDialogFragment : DialogFragment() {

    private var bContact: Button? = null
    private var imgLinked: ImageView? = null
    private var imgGit: ImageView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.dialog_info, container, false)
        settingInterface(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun settingInterface(view: View){
        bContact = view.findViewById(R.id.b_contact)
        imgLinked = view.findViewById(R.id.img_linked_in)
        imgGit = view.findViewById(R.id.img_github)

        bContact?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:pepe.gonzalez.cas@gmail.com") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hello there !")
            startActivity(intent)
        }

        imgGit?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/pepeglez")
            startActivity(intent)
        }

        imgLinked?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.linkedin.com/in/pepe-gonzalez/")
            startActivity(intent)
        }
    }

}