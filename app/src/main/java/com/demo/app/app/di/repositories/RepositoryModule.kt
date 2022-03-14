package com.demo.app.app.di.repositories

import com.demo.app.data.repositories.UserRepositoryImpl
import com.demo.app.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}