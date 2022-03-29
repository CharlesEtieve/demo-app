package com.demo.app.domain.repositories

import com.demo.app.domain.models.DomainUserPage
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun getUserPage(page: Int): Single<DomainUserPage>
}