package com.example.data.usecases

import arrow.core.Either
import arrow.core.Failure
import com.example.data.interactor.UseCase
import com.example.domain.UserDetailsRepository
import com.example.domain.entities.Gender
import com.example.domain.exception.FirebaseResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class ChangeProfileUserGender @Inject constructor(var userRepository: UserDetailsRepository) :
    UseCase<FirebaseResult, Gender>() {

    override suspend fun run(params: Gender): Either<Failure, FirebaseResult> =
        userRepository.updateUserGender(params)
}

