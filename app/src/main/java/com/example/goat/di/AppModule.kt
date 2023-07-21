package com.example.goat.di

import com.example.goat.common.BASE_URL
import com.example.goat.data.remote.api.GotqApi
import com.example.goat.data.repository.AuthenticationDataSource
import com.example.goat.data.repository.ContributionQuizDataSource
import com.example.goat.data.repository.GotqDataSource
import com.example.goat.data.repository.ProfileDataSource
import com.example.goat.data.repository.UserDataSource
import com.example.goat.domain.repository.AuthenticationRepository
import com.example.goat.domain.repository.ContributionQuizRepository
import com.example.goat.domain.repository.GotqRepository
import com.example.goat.domain.repository.ProfileRepository
import com.example.goat.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGotqApi(): GotqApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(GotqApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGotqRepository(gotqApi: GotqApi): GotqRepository {
        return GotqDataSource(gotqApi)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): AuthenticationRepository {
        return AuthenticationDataSource(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(firestore: FirebaseFirestore, firebaseStorage: FirebaseStorage, authenticationRepository: AuthenticationRepository): ProfileRepository {
        return ProfileDataSource(firestore, firebaseStorage,authenticationRepository)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore, authenticationRepository: AuthenticationRepository): UserRepository {
        return UserDataSource(firestore,authenticationRepository)
    }

    @Provides
    @Singleton
    fun provideContributionQuizRepository(firestore: FirebaseFirestore): ContributionQuizRepository {
        return ContributionQuizDataSource(firestore)
    }
}