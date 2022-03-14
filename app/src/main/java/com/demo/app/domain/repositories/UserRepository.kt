package com.demo.app.domain.repositories

import com.demo.app.domain.models.DomainUserPage
import io.reactivex.rxjava3.core.Observable

interface UserRepository {
    fun getUserPage(page: Int): Observable<DomainUserPage>
}