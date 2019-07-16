package com.example.loquicleanarchitecture.view.profile

import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionManager
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileAgeRangeChoice
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileGenderChoice
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileNicknameChoice
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.view_profile_user_details.*
import org.jetbrains.anko.toast

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    var currentViewHolder: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initListeners()
        swapView(image1)

        // TODO Add delete button for photos https://github.com/stfalcon-studio/ChatKit/blob/master/docs/COMPONENT_MESSAGE_INPUT.MD
        // Todo Add save button on toolbar
    }

    fun swapView(v: View) {
        d("Swap", v.id.toString())
        TransitionManager.beginDelayedTransition(layout)
        placeholder1.setContentId(v.id)

        v.setOnClickListener { loadImagePicker(v) }

        // Reset previous listener
        currentViewHolder?.setOnClickListener(this)
        this.currentViewHolder = v
    }

    private fun initListeners() {
        constraintLayoutNickname.setOnClickListener(this)
        constraintLayoutGender.setOnClickListener(this)
        constraintLayoutAge.setOnClickListener(this)
        placeholder1.setOnClickListener(this)
        image1.setOnClickListener(this)
        image2.setOnClickListener(this)
        image3.setOnClickListener(this)
        image4.setOnClickListener(this)
    }

    private fun loadImagePicker(v: View) {
        toast("placeholder")
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this);
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.constraintLayoutNickname -> DialogProfileNicknameChoice(this).show()
            R.id.constraintLayoutGender -> DialogProfileGenderChoice(this).show()
            R.id.constraintLayoutAge -> DialogProfileAgeRangeChoice(this).show()
            R.id.image1 -> swapView(v)
            R.id.image2 -> swapView(v)
            R.id.image3 -> swapView(v)
            R.id.image4 -> swapView(v)
            else -> toast(placeholder1.content.id)
        }
    }
}
