package com.demo.app.domain.repositories

import com.demo.app.DataFixtures
import com.demo.app.DomainExceptionsFixtures
import com.demo.app.data.database.dao.UserDao
import com.demo.app.data.models.entityMapping.toEntity
import com.demo.app.data.repositories.UserRepositoryImpl
import com.demo.app.data.vendors.network.api.UserApi
import com.demo.app.domain.models.DomainUserPage
import com.eurosportdemo.app.BuildConfig
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTests {
    private val mockUserApi: UserApi = mock()
    private val mockUserDao: UserDao = mock()
    private var userRepository = UserRepositoryImpl(mockUserApi, mockUserDao)

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockUserApi, mockUserDao)
    }

    @Test
    fun testGetUserPageSuccess() {
        val expectedPage = 1
        val expectedUserPage = DataFixtures.UserListResponseUtils.create()
        val expectedDomainUserPage = DomainUserPage(
            userList = expectedUserPage.toDomain(),
            canRefresh = true
        )
        val expectedUserEntity = expectedDomainUserPage.userList.map { user ->
            user.toEntity()
        }
        whenever(mockUserApi.getUserPage(any(), any(), any())).thenReturn(Single.just(expectedUserPage))
        whenever(mockUserDao.insertUsers(any())).thenReturn(Completable.complete())

        userRepository.getUserPage(expectedPage)
            .test()
            .assertValue(expectedDomainUserPage)

        verify(mockUserApi).getUserPage(BuildConfig.SEED, BuildConfig.RESULT, expectedPage.toString())
        verify(mockUserDao).insertUsers(expectedUserEntity)
    }

    @Test
    fun testGetUserPageOffline() {
        val expectedPage = 1
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        val expectedUserListResponse = DataFixtures.UserListResponseUtils.create()
        val expectedDomainUserPage = DomainUserPage(
            userList = expectedUserListResponse.toDomain(),
            canRefresh = false
        )
        val expectedUserEntityList = listOf(DataFixtures.UserEntityUtils.create())
        whenever(mockUserApi.getUserPage(any(), any(), any())).thenReturn(Single.error(expectedDomainException))
        whenever(mockUserDao.getAllUser()).thenReturn(Observable.just(expectedUserEntityList))

        userRepository.getUserPage(expectedPage)
            .test()
            .assertValue(expectedDomainUserPage)

        verify(mockUserApi).getUserPage(BuildConfig.SEED, BuildConfig.RESULT, expectedPage.toString())
        verify(mockUserDao).getAllUser()
    }

    @Test
    fun testGetUserPageFailure() {
        val expectedPage = 1
        val expectedDomainException = DomainExceptionsFixtures.DomainNetworkUtils.createInternalError("error")
        whenever(mockUserApi.getUserPage(any(), any(), any())).thenReturn(Single.error(expectedDomainException))
        whenever(mockUserDao.getAllUser()).thenReturn(Observable.error(expectedDomainException))

        userRepository.getUserPage(expectedPage)
            .test()
            .assertError(expectedDomainException)

        verify(mockUserApi).getUserPage(BuildConfig.SEED, BuildConfig.RESULT, expectedPage.toString())
        verify(mockUserDao).getAllUser()
    }
}