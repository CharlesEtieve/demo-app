package com.demo.app.domain.useCase

import com.demo.app.domain.models.DomainUserPage
import com.demo.app.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetUserPageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    private var page = 0
    private var userPageArrayList = ArrayList<DomainUserPage>()

    fun invoke(
        refresh: Boolean = false
    ): Observable<List<DomainUserPage>> =
        Observable.defer {
            if(refresh) {
                userPageArrayList = ArrayList()
                page = 1
            } else {
                page += 1
            }
            userRepository.getUserPage(page)
                .map {
                    userPageArrayList.apply {
                        add(it)
                    }
                }.toObservable()
        }
}