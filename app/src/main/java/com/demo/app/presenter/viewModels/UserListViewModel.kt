package com.demo.app.presenter.viewModels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.demo.app.domain.useCase.GetUserPageUseCase
import com.demo.app.presenter.models.UIUserItem
import com.demo.app.presenter.models.uiItemMapping.toUIItem
import com.demo.app.presenter.util.MySchedulers
import com.eurosportdemo.app.R
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserPage: GetUserPageUseCase,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    sealed class ViewState {
        data class ShowUserList(val userList: List<UIUserItem>) : ViewState()
        object ShowNoData : ViewState()
        data class ShowErrorMessage(@StringRes val message: Int) : ViewState()
    }

    fun getViewState(
        load: Observable<Unit>,
        refresh: Observable<Unit>
    ): Observable<ViewState> =
        Observable.merge(
            load.map { false },
            refresh.map { true }
        )
            .toFlowable(BackpressureStrategy.DROP) //load/refresh should be finished before load/refresh again
            .toObservable()
            .flatMap {
                getUserPage.invoke(
                    refresh = it
                ).subscribeOn(mySchedulers.io)
            }
            .observeOn(mySchedulers.main)
            .map { userPageList ->
                val userList = userPageList.toUIItem()
                if (userList.isEmpty()) {
                    ViewState.ShowNoData
                } else {
                    ViewState.ShowUserList(
                        userList
                    )
                }
            }
            .onErrorResumeNext {
                Observable.just(ViewState.ShowErrorMessage(R.string.generic_error))
            }
}