package com.example.loquicleanarchitecture.view.main.viewPager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.usecases.QueryUsers
import com.example.domain.entities.UserEntity
import javax.inject.Inject

class RandomChatsViewModel @Inject constructor(val queryUsers: QueryUsers) : ViewModel() {
    private val TAG: String? = this.javaClass.name

    private val nextUser = MutableLiveData<Pair<*, *>>()
    fun getNextUserLiveData() = nextUser


    fun queryUsers(currentUser: UserEntity) =
        queryUsers(currentUser) { it.fold(::handleFailure, ::handleSuccess) }


    private fun handleSuccess(firebaseResult: Any?) {
        Log.d(TAG, firebaseResult.toString())

        when (firebaseResult) {
            is Pair<*, *> -> {
                firebaseResult?.run { nextUser.value = firebaseResult }
            }
        }


    }

    private fun handleFailure(firebaseResult: Any?) {}
}