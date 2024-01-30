package gyul.songgyubin.daytogo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gyul.songgyubin.data.repository.auth.AuthRepositoryImpl
import gyul.songgyubin.data.repository.location.LocationRepositoryImpl
import gyul.songgyubin.domain.repository.AuthRepository
import gyul.songgyubin.domain.repository.LocationRepository
import gyul.songgyubin.domain.usecase.*

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideLocationRepositoryImpl():LocationRepository = LocationRepositoryImpl()

    @Provides
    fun provideAuthRepositoryImpl():AuthRepository = AuthRepositoryImpl()

    @Provides
    fun provideAddLocationInfoUseCase(repository: LocationRepository):AddLocationInfoUseCase =
        AddLocationInfoUseCase(repository)

    @Provides
    fun provideFirebaseCreateUserInfoDbUseCase(repository: AuthRepository):FirebaseCreateUserInfoDbUseCase =
        FirebaseCreateUserInfoDbUseCase(repository)

    @Provides
    fun provideFirebaseCreateUserUseCase(repository: AuthRepository):FirebaseCreateUserUseCase =
        FirebaseCreateUserUseCase(repository)

    @Provides
    fun provideFirebaseLoginUseCase(repository: AuthRepository):FirebaseLoginUseCase = FirebaseLoginUseCase(repository)

    @Provides
    fun provideGetRemoteSavedLocationInfoUseCase(repository: LocationRepository) =
        GetRemoteSavedLocationInfoUseCase(repository)
}