package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.MainViewModel
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.dialog_drawer_age_range_choice.view.*
import javax.inject.Inject

class DialogDrawerSearchAge : DaggerDialogFragment() {
    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mainViewModel = ViewModelProvider(activity!!.viewModelStore, viewModelFactory).get(MainViewModel::class.java)
        var userData = mainViewModel.userData.value

        var firstValue: Int = userData!!.preferences_age_range_min
        var secondValue: Int = userData.preferences_age_range_max

        Log.d(TAG, userData.toString())
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_drawer_age_range_choice, null)

        with(view) {

            with(drawer_age_range_picker_first) {
                maxValue = 99
                minValue = 1
                value = firstValue
                setOnValueChangedListener { _, _, newVal -> firstValue = newVal }
            }
            with(drawer_age_range_picker_second) {
                maxValue = 99
                minValue = 1
                value = secondValue
                setOnValueChangedListener { _, _, newVal -> secondValue = newVal }
            }
        }

        return activity?.let {
            // Use the Builder class for convenient dialog construction

            //Todo
            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.pl_drawer_dialog_ageRangeTitle)
                .setPositiveButton(
                    R.string.pl_confirm
                ) { _, _ ->
                        userData.preferences_age_range_min = firstValue
                        userData.preferences_age_range_max = secondValue

                }
                .setNegativeButton(
                    R.string.pl_cancel
                ) { _, _ ->
                    // User cancelled the dialog
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}