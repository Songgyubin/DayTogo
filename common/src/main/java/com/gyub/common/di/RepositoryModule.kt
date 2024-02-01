package com.gyub.common.di

import dagger.Provides
import gyul.songgyubin.data.auth.repository.AuthRepositoryImpl
import gyul.songgyubin.data.location.repository.LocationRepositoryImpl
import gyul.songgyubin.domain.location.repository.LocationRepository
import gyul.songgyubin.domain.location.usecase.AddLocationInfoUseCase
import gyul.songgyubin.domain.repository.AuthRepository

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
}