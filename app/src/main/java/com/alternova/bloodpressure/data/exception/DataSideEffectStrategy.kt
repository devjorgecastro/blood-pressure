package com.alternova.bloodpressure.data.exception

import com.alternova.bloodpressure.domain.exception.DomainException

interface DataSideEffectStrategy {

    val managedExceptions: List<Class<out Throwable>>
    fun getDomainException(error: Throwable, data: Any?): DomainException
    fun hasManagedError(error: Throwable): Boolean
}
