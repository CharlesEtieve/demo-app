package com.demo.app.domain.useCases

import com.demo.app.DomainExceptionsFixtures
import com.demo.app.DomainFixtures
import com.demo.app.domain.repositories.UserRepository
import com.demo.app.domain.useCase.GetUserPageUseCase
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Test
import io.reactivex.rxjava3.core.Single
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUserPageUseCaseTests {
    private val mockUserRepository: UserRepository = mock()
    private var useCase = GetUserPageUseCase(mockUserRepository)

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockUserRepository)
    }

    @Test
    fun testInvokeSuccess() {
        val expectedPage = 1
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList = listOf(expectedUserPage)
        whenever(mockUserRepository.getUserPage(any())).thenReturn(Single.just(expectedUserPage))

        useCase.invoke()
            .test()
            .assertValue(expectedUserPageList)

        verify(mockUserRepository).getUserPage(expectedPage)
    }

    @Test
    fun testInvokeFailure() {
        val expectedPage = 1
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockUserRepository.getUserPage(any())).thenReturn(Single.error(expectedDomainException))

        useCase.invoke()
            .test()
            .assertError(expectedDomainException)

        verify(mockUserRepository).getUserPage(expectedPage)
    }

    @Test
    fun testTwoInvokeSuccess() {
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList1 = listOf(expectedUserPage)
        val expectedUserPageList2 = listOf(expectedUserPage, expectedUserPage)
        whenever(mockUserRepository.getUserPage(any())).thenReturn(Single.just(expectedUserPage))

        useCase.invoke()
            .test()
            .assertValue(expectedUserPageList1)

        useCase.invoke()
            .test()
            .assertValue(expectedUserPageList2)

        verify(mockUserRepository).getUserPage(1)
        verify(mockUserRepository).getUserPage(2)
    }

    @Test
    fun testInvokeWithRefreshSuccess() {
        val expectedPage = 1
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList = listOf(expectedUserPage)
        whenever(mockUserRepository.getUserPage(any())).thenReturn(Single.just(expectedUserPage))

        useCase.invoke(refresh = true)
            .test()
            .assertValue(expectedUserPageList)

        verify(mockUserRepository).getUserPage(expectedPage)
    }

    @Test
    fun testTwoInvokeWithRefreshSuccess() {
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageList1 = listOf(expectedUserPage)
        whenever(mockUserRepository.getUserPage(any())).thenReturn(Single.just(expectedUserPage))

        useCase.invoke()
            .test()
            .assertValue(expectedUserPageList1)

        useCase.invoke(refresh = true)
            .test()
            .assertValue(expectedUserPageList1)

        verify(mockUserRepository, times(2)).getUserPage(1)
    }

    @Test
    fun testInvokeWithRefreshFailure() {
        val expectedPage = 1
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockUserRepository.getUserPage(any())).thenReturn(Single.error(expectedDomainException))

        useCase.invoke(refresh = true)
            .test()
            .assertError(expectedDomainException)

        verify(mockUserRepository).getUserPage(expectedPage)
    }
}