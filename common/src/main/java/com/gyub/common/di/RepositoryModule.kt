package com.gyub.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gyul.songgyubin.data.auth.repository.AuthRepositoryImpl
import gyul.songgyubin.data.location.repository.LocationRepositoryImpl
import gyul.songgyubin.domain.location.repository.LocationRepository
import gyul.songgyubin.domain.repository.AuthRepository

/**
 * Repository Module (interface + @binds)
 *
 * @author   Gyub
 * @created  2024/01/30
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

    @Binds
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}