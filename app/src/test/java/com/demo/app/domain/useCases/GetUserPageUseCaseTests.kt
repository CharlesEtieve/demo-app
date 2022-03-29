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
    fun testInvokeAndRefreshSuccess() {
        val expectedPage = 1
        val expectedUserPage = DomainFixtures.DomainUserPageUtils.create()
        val expectedUserPageListLoad = listOf(expectedUserPage)
        val expectedUserPageListRefresh = listOf(expectedUserPage)
        whenever(mockUserRepository.getUserPage(any())).thenReturn(Single.just(expectedUserPage))

        useCase.invoke()
            .test()
            .assertValue(expectedUserPageListLoad)

        useCase.invoke(
            refresh = true
        )
            .test()
            .assertValue(expectedUserPageListRefresh)

        verify(mockUserRepository, times(2)).getUserPage(expectedPage)
    }

    @Test
    fun testInvokeAndRefreshFailure() {
        val expectedPage = 1
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockUserRepository.getUserPage(any())).thenReturn(Single.error(expectedDomainException))

        useCase.invoke()
            .test()
            .assertError(expectedDomainException)

        useCase.invoke(
            refresh = true
        )
            .test()
            .assertError(expectedDomainException)

        verify(mockUserRepository, times(2)).getUserPage(expectedPage)
    }
}