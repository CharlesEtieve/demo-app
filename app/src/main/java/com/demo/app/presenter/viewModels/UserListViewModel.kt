package com.demo.app.presenter.viewModels

import androidx.annotation.StringRes
import com.eurosportdemo.app.R
import com.demo.app.domain.useCase.GetUserPageUseCase
import com.demo.app.presenter.models.UIUserItem
import com.demo.app.presenter.models.uiItemMapping.toUIItem
import com.demo.app.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserPage: GetUserPageUseCase,
    private val mySchedulers: MySchedulers,
    val viewState: BehaviorSubject<ViewState>
) : BaseViewModel() {

    sealed class ViewState {
        data class ShowUserList(val userList: List<UIUserItem>) : ViewState()
        object ShowNoData : ViewState()
        data class ShowErrorMessage(@StringRes val message: Int) : ViewState()
    }

    fun setup() {
        loadPage()
    }

    fun refresh() {
        loadPage(true)
    }

    fun loadMore() {
        loadPage()
    }

    fun itemClicked(user: UIUserItem) {
        //not implemented yet
    }

    private fun loadPage(refresh: Boolean = false) {
        getUserPage.invoke(refresh)
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)
            .subscribe(
                { userPageList ->
                    val userList = userPageList.toUIItem()
                    if (userList.isEmpty()) {
                        viewState.onNext(
                            ViewState.ShowNoData
                        )
                    } else {
                        viewState.onNext(
                            ViewState.ShowUserList(
                                userList
                            )
                        )
                    }
                },
                {
                    viewState.onNext(
                        ViewState.ShowErrorMessage(R.string.generic_error)
                    )
                }
            ).addTo(disposable)
    }
}