package com.demo.app.domain.viewModels

import com.demo.app.DomainExceptionsFixtures
import com.demo.app.DomainFixtures
import com.demo.app.TestMySchedulers
import com.demo.app.domain.models.DomainUserPage
import com.demo.app.domain.useCase.GetUserPageUseCase
import com.demo.app.presenter.models.UIUserItem
import com.demo.app.presenter.models.uiItemMapping.toUIItem
import com.demo.app.presenter.viewModels.UserListViewModel
import com.eurosportdemo.app.R
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTests {
    private val mockGetUserPageUseCase: GetUserPageUseCase = mock()
    private val mockViewState: BehaviorSubject<UserListViewModel.ViewState> = mock()
    private val testNetworkSchedulers = TestMySchedulers()
    private val viewModel = UserListViewModel(mockGetUserPageUseCase, testNetworkSchedulers, mockViewState)

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockGetUserPageUseCase, mockViewState)
    }

    @Test
    fun testSetupSuccess() {
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList = listOf(expectedUserPage)
        val expectedUIUserItemList = getUIUserItemList(expectedUserPageList)
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.just(expectedUserPageList))

        viewModel.setup()

        verify(mockGetUserPageUseCase).invoke()
        verify(mockViewState).onNext(UserListViewModel.ViewState.ShowUserList(expectedUIUserItemList))
    }

    @Test
    fun testSetupFailure() {
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.error(expectedDomainException))

        viewModel.setup()

        verify(mockGetUserPageUseCase).invoke()
        verify(mockViewState).onNext(UserListViewModel.ViewState.ShowErrorMessage(R.string.generic_error))
    }

    @Test
    fun testLoadMoreSuccess() {
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList = listOf(expectedUserPage)
        val expectedUIUserItemList = getUIUserItemList(expectedUserPageList)
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.just(expectedUserPageList))

        viewModel.loadMore()

        verify(mockGetUserPageUseCase).invoke()
        verify(mockViewState).onNext(UserListViewModel.ViewState.ShowUserList(expectedUIUserItemList))
    }

    @Test
    fun testLoadMoreFailure() {
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.error(expectedDomainException))

        viewModel.loadMore()

        verify(mockGetUserPageUseCase).invoke()
        verify(mockViewState).onNext(UserListViewModel.ViewState.ShowErrorMessage(R.string.generic_error))
    }

    @Test
    fun testRefreshSuccess() {
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList = listOf(expectedUserPage)
        val expectedUIUserItemList = getUIUserItemList(expectedUserPageList)
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.just(expectedUserPageList))

        viewModel.refresh()

        verify(mockGetUserPageUseCase).invoke(true)
        verify(mockViewState).onNext(UserListViewModel.ViewState.ShowUserList(expectedUIUserItemList))
    }

    @Test
    fun testRefreshFailure() {
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.error(expectedDomainException))

        viewModel.refresh()

        verify(mockGetUserPageUseCase).invoke(true)
        verify(mockViewState).onNext(UserListViewModel.ViewState.ShowErrorMessage(R.string.generic_error))
    }

    private fun getUIUserItemList(userPageList: List<DomainUserPage>): List<UIUserItem> =
        arrayListOf<UIUserItem>().apply {
            for(userPage in userPageList) {
                addAll(userPage.userList.map { it.toUIItem() })
                add(UIUserItem.Progress)
            }
        }
}