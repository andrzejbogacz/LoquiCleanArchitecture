package com.example.loquicleanarchitecture.view.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.domain.entities.Gender
import com.example.domain.entities.UserEntity
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.helper.observe
import com.example.loquicleanarchitecture.helper.viewModel
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileAgeChoice
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileGenderChoice
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileNicknameChoice
import com.example.loquicleanarchitecture.view.main.MainViewModel
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.view_profile_user_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileFragment : DaggerFragment(),
    View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    lateinit var mainViewModel: MainViewModel

    var currentViewHolder: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initListeners()

        mainViewModel = viewModel(viewModelFactory) {
            observe(getUserDataLiveData(), ::updateUI)
        }

        // TODO Add delete button for photos https://github.com/stfalcon-studio/ChatKit/blob/master/docs/COMPONENT_MESSAGE_INPUT.MD
        // Todo Add save button on toolbar
    }

    private fun initListeners() {
        constraintLayoutNickname.setOnClickListener(this)
        layout_constraint_profile_gender.setOnClickListener(this)
        constraintLayoutAge.setOnClickListener(this)
        iv_photo1.setOnClickListener(this)
        iv_photo2.setOnClickListener(this)
        iv_photo3.setOnClickListener(this)
        iv_photo4.setOnClickListener(this)
        iv_photo5.setOnClickListener(this)
        iv_photo6.setOnClickListener(this)
    }

    private fun loadImagePicker(v: View) {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
            .start(requireActivity())
        currentViewHolder = v as ImageView
    }

    private fun updateUI(userEntity: UserEntity) {
        textView_profile_nickname_value.text = userEntity.nickname
        textView_profile_age_value.text = userEntity.age.toString()

        when (userEntity.gender) {
            Gender.FEMALE -> textView_profile_gender_value.text =
                getString(R.string.drawer_dialog_genderFemale)
            Gender.MALE -> textView_profile_gender_value.text =
                getString(R.string.drawer_dialog_genderMale)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.constraintLayoutNickname -> DialogProfileNicknameChoice().show(
                childFragmentManager,
                "nickname"
            )
            R.id.layout_constraint_profile_gender -> DialogProfileGenderChoice().show(
                childFragmentManager,
                "gender"
            )
            R.id.constraintLayoutAge -> DialogProfileAgeChoice().show(childFragmentManager, "age")
            R.id.iv_photo1, R.id.iv_photo2, R.id.iv_photo3, R.id.iv_photo4, R.id.iv_photo5, R.id.iv_photo6 -> loadImagePicker(
                v
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                setPhoto(resultUri)
                mainViewModel.uploadProfileUserPhoto(resultUri.toString())
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun setPhoto(resultUri: Uri?) {
        Picasso.get()
            .load(resultUri)
            .fit()
            .into(currentViewHolder)
    }
}