package com.demo.app.data.utils

import androidx.annotation.VisibleForTesting
import com.demo.app.data.models.domainMappingProtocols.DomainExceptionConvertible
import com.demo.app.data.models.exceptions.DataAPIDecodeException
import com.demo.app.domain.models.errors.DomainError
import com.demo.app.domain.models.DomainException
import com.demo.app.domain.models.DomainNetworkException
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.serialization.SerializationException

fun Completable.throwDomainExceptionOnError(): Completable = doOnError { throw getDomainException(it) }
fun <T : Any> Observable<T>.throwDomainExceptionOnError(): Observable<T> = doOnError { throw getDomainException(it) }
fun <T : Any> Single<T>.throwDomainExceptionOnError(): Single<T> = doOnError { throw getDomainException(it) }

@VisibleForTesting
internal fun getDomainException(error: Throwable?): DomainException =
    when (error) {
        is SerializationException, is DataAPIDecodeException ->
            DomainNetworkException.InternalError(
                DomainError(error.message ?: "SerializationException or DataAPIDecodeException")
            )
        is DomainExceptionConvertible -> error.toDomain()
        else -> DomainNetworkException.InternalError(
            DomainError(error?.message ?: "DomainNetworkException")
        )
    }
