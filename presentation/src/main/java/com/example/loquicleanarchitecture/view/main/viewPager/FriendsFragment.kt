package com.example.loquicleanarchitecture.view.main.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.domain.entities.Dialog
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import javax.inject.Inject

class FriendsFragment : ChatManager(), DialogsListAdapter.OnDialogClickListener<Dialog>,
    DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    lateinit var dialogsAdapter: DialogsListAdapter<Dialog>

    @Inject
    lateinit var picasso: Picasso

    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageLoader = ImageLoader { imageView, url, payload -> picasso.load(url).into(imageView) }
        initDummyAdapter()
    }

    private fun initRealAdapter() {
        dialogsAdapter = DialogsListAdapter(imageLoader)
        dialogsAdapter.setItems(DialogsFixtures.dialogs)

        dialogsAdapter.setOnDialogClickListener(this)
        dialogsAdapter.setOnDialogLongClickListener(this)

        dialogsList.setAdapter(dialogsAdapter)
    }

    private fun initDummyAdapter() {
        dialogsAdapter = DialogsListAdapter(imageLoader)
        dialogsAdapter.setItems(DialogsFixtures.dialogs)

        dialogsAdapter.setOnDialogClickListener(this)
        dialogsAdapter.setOnDialogLongClickListener(this)

        dialogsList.setAdapter(dialogsAdapter)
    }

    override fun onDialogLongClick(dialog: Dialog) {
        makeText(
            context!!.applicationContext,
            dialog.dialogName,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDialogClick(dialog: Dialog?) {
        //todo DIALOG info, co skad dlaczego czy sie przyda i czy cos zmieniac
        //startChat(Pair(1,1))
    }
}



