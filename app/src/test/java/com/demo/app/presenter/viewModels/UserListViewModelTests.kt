package com.demo.app.presenter.viewModels

import com.demo.app.DomainExceptionsFixtures
import com.demo.app.DomainFixtures
import com.demo.app.TestMySchedulers
import com.demo.app.domain.models.DomainUserPage
import com.demo.app.domain.useCase.GetUserPageUseCase
import com.demo.app.presenter.models.UIUserItem
import com.demo.app.presenter.models.uiItemMapping.toUIItem
import com.eurosportdemo.app.R
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTests {
    private val mockGetUserPageUseCase: GetUserPageUseCase = mock()
    private val testNetworkSchedulers = TestMySchedulers()
    private val viewModel = UserListViewModel(mockGetUserPageUseCase, testNetworkSchedulers)

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockGetUserPageUseCase)
    }

    @Test
    fun testViewCreatedSuccess() {
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList = listOf(expectedUserPage)
        val expectedUIUserItemList = getUIUserItemList(expectedUserPageList)
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.just(expectedUserPageList))

        viewModel.getViewState(
            viewCreated = Observable.just(Unit),
            displayProgress = Observable.never(),
            refresh = Observable.never()
        )
            .test()
            .assertValue(UserListViewModel.ViewState.ShowUserList(expectedUIUserItemList))

        verify(mockGetUserPageUseCase).invoke(any())
    }

    @Test
    fun testViewCreatedNoData() {
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.just(listOf()))

        viewModel.getViewState(
            viewCreated = Observable.just(Unit),
            displayProgress = Observable.never(),
            refresh = Observable.never()
        )
            .test()
            .assertValue(UserListViewModel.ViewState.ShowNoData)

        verify(mockGetUserPageUseCase).invoke(any())
    }

    @Test
    fun testViewCreatedFailure() {
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.error(expectedDomainException))

        viewModel.getViewState(
            viewCreated = Observable.just(Unit),
            displayProgress = Observable.never(),
            refresh = Observable.never()
        )
            .test()
            .assertValue(UserListViewModel.ViewState.ShowErrorMessage(R.string.generic_error))

        verify(mockGetUserPageUseCase).invoke(any())
    }

    @Test
    fun testDisplayProgressSuccess() {
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList = listOf(expectedUserPage)
        val expectedUIUserItemList = getUIUserItemList(expectedUserPageList)
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.just(expectedUserPageList))

        viewModel.getViewState(
            viewCreated = Observable.just(Unit),
            displayProgress = Observable.just(Unit),
            refresh = Observable.never()
        )
            .test()
            .assertValueAt(0, UserListViewModel.ViewState.ShowUserList(expectedUIUserItemList))
            .assertValueAt(1, UserListViewModel.ViewState.ShowUserList(expectedUIUserItemList))

        verify(mockGetUserPageUseCase, times(2)).invoke(any())
    }

    @Test
    fun testDisplayProgressFailure() {
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.error(expectedDomainException))

        viewModel.getViewState(
            viewCreated = Observable.just(Unit),
            displayProgress = Observable.just(Unit),
            refresh = Observable.never()
        )
            .test()
            .assertValue(UserListViewModel.ViewState.ShowErrorMessage(R.string.generic_error))

        verify(mockGetUserPageUseCase).invoke(any())
    }

    @Test
    fun testRefreshSuccess() {
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList = listOf(expectedUserPage)
        val expectedUIUserItemList = getUIUserItemList(expectedUserPageList)
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.just(expectedUserPageList))

        viewModel.getViewState(
            viewCreated = Observable.just(Unit),
            displayProgress = Observable.never(),
            refresh = Observable.just(Unit)
        )
            .test()
            .assertValueAt(0, UserListViewModel.ViewState.ShowUserList(expectedUIUserItemList))
            .assertValueAt(1, UserListViewModel.ViewState.ShowUserList(expectedUIUserItemList))

        verify(mockGetUserPageUseCase, times(2)).invoke(any())
    }

    @Test
    fun testRefreshFailure() {
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockGetUserPageUseCase.invoke(any())).thenReturn(Observable.error(expectedDomainException))

        viewModel.getViewState(
            viewCreated = Observable.just(Unit),
            displayProgress = Observable.never(),
            refresh = Observable.just(Unit)
        )
            .test()
            .assertValue(UserListViewModel.ViewState.ShowErrorMessage(R.string.generic_error))

        verify(mockGetUserPageUseCase).invoke(any())
    }

    private fun getUIUserItemList(userPageList: List<DomainUserPage>): List<UIUserItem> =
        arrayListOf<UIUserItem>().apply {
            for(userPage in userPageList) {
                addAll(userPage.userList.map { it.toUIItem() })
                add(UIUserItem.Progress)
            }
        }
}