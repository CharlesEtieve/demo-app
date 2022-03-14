package com.demo.app

import com.demo.app.domain.models.DomainNetworkException
import com.demo.app.domain.models.errors.DomainError

class DomainExceptionsFixtures {

    object DomainNetworkUtils {
        fun createInternalError(message: String) = DomainNetworkException.InternalError(DomainError(message))
    }
}