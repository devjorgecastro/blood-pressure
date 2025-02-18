package com.alternova.bloodpressure.domain.exception

open class DomainException(override val message: String = String()): Exception(message)
data class StorageLimitExceededException(override val message: String): DomainException()
data class DiskReadWriteException(override val message: String): DomainException()
data class GeneralDatabaseException(
    override val message: String,
    val localData: Any? = null
): DomainException()
