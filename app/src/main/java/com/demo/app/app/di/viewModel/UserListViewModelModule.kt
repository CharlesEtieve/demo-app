package com.demo.app.app.di.viewModel

import com.demo.app.presenter.viewModels.UserListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.rxjava3.subjects.BehaviorSubject

@InstallIn(ViewModelComponent::class)
@Module
object UserListViewModelModule {

    @Provides
    fun providesViewState(): BehaviorSubject<UserListViewModel.ViewState> = BehaviorSubject.create()
}