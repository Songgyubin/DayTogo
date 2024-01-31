package com.gyub.common.di

import dagger.Provides
import gyul.songgyubin.data.auth.repository.AuthRepositoryImpl
import gyul.songgyubin.data.location.repository.LocationRepositoryImpl
import gyul.songgyubin.domain.repository.AuthRepository
import gyul.songgyubin.domain.repository.LocationRepository
import gyul.songgyubin.domain.usecase.AddLocationInfoUseCase
import gyul.songgyubin.domain.usecase.FirebaseCreateUserInfoDbUseCase
import gyul.songgyubin.domain.usecase.FirebaseCreateUserUseCase
import gyul.songgyubin.domain.usecase.FirebaseLoginUseCase
import gyul.songgyubin.domain.usecase.GetRemoteSavedLocationInfoUseCase

/**
 * Repository Module (interface + @binds)
 *
 * @author   Gyub
 * @created  2024/01/30
 */
interface RepositoryModule {
    @Provides
    fun provideLocationRepositoryImpl(): LocationRepository = LocationRepositoryImpl()

    @Provides
    fun provideAuthRepositoryImpl(): AuthRepository = AuthRepositoryImpl()

    @Provides
    fun provideAddLocationInfoUseCase(repository: LocationRepository): AddLocationInfoUseCase =
        AddLocationInfoUseCase(repository)

    @Provides
    fun provideFirebaseCreateUserInfoDbUseCase(repository: AuthRepository): FirebaseCreateUserInfoDbUseCase =
        FirebaseCreateUserInfoDbUseCase(repository)

    @Provides
    fun provideFirebaseCreateUserUseCase(repository: AuthRepository): FirebaseCreateUserUseCase =
        FirebaseCreateUserUseCase(repository)

    @Provides
    fun provideFirebaseLoginUseCase(repository: AuthRepository): FirebaseLoginUseCase = FirebaseLoginUseCase(repository)

    @Provides
    fun provideGetRemoteSavedLocationInfoUseCase(repository: LocationRepository) =
        GetRemoteSavedLocationInfoUseCase(repository)
}