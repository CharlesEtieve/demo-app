package com.demo.app.app.di.viewModel

import com.demo.app.presenter.util.MySchedulers
import com.demo.app.presenter.util.MySchedulersImpl
import com.demo.app.presenter.viewModels.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.subjects.BehaviorSubject

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsPresenterModule {

    @Binds
    abstract fun bindNetworkSchedulers(networkSchedulers: MySchedulersImpl): MySchedulers
}