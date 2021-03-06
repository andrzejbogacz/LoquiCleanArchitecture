package com.example.loquicleanarchitecture.di

import com.example.data.FirebaseRepositoryImpl
import com.example.data.SearchRepositoryImpl
import com.example.domain.SearchRepository
import com.example.domain.UserDetailsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        firebaseStorage: StorageReference
    ): UserDetailsRepository {
        return FirebaseRepositoryImpl(firebaseFirestore, firebaseAuth, firebaseStorage)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(
        firebaseFirestore: FirebaseFirestore
    ): SearchRepository {
        return SearchRepositoryImpl(firebaseFirestore)
    }

    @Singleton
    @Provides
    internal fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    internal fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(firebaseAuth: FirebaseAuth): StorageReference {
        return FirebaseStorage.getInstance().reference.child(firebaseAuth.currentUser!!.uid)
    }


}