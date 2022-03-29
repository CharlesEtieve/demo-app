package com.demo.app.data.repositories

import com.demo.app.data.database.dao.UserDao
import com.demo.app.data.models.entityMapping.toEntity
import com.demo.app.data.vendors.network.api.UserApi
import com.demo.app.domain.models.DomainUserPage
import com.demo.app.domain.repositories.UserRepository
import com.eurosportdemo.app.BuildConfig
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao
) : UserRepository {

    override fun getUserPage(page: Int): Single<DomainUserPage> =
        userApi.getUserPage(
            seed = BuildConfig.SEED,
            results = BuildConfig.RESULT,
            page = page.toString()
        )
            .map {
                DomainUserPage(
                    userList = it.toDomain(),
                    canRefresh = true
                )
            }
            .flatMap { userPage ->
                userDao.insertUsers(
                    userPage.userList.map { user ->
                        user.toEntity()
                    }
                ).toSingle { userPage }
            }
            .onErrorResumeNext {
                userDao.getAllUser()
                    .map { userList ->
                        userList.map { user ->
                            user.toDomain()
                        }
                    }.map { userList ->
                        DomainUserPage(
                            userList = userList,
                            canRefresh = false
                        )
                    }.firstOrError()
            }
}