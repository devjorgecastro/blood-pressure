package com.alternova.bloodpressure.data.exception

import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.alternova.bloodpressure.domain.exception.DiskReadWriteException
import com.alternova.bloodpressure.domain.exception.DomainException
import com.alternova.bloodpressure.domain.exception.GeneralDatabaseException
import com.alternova.bloodpressure.domain.exception.StorageLimitExceededException

class ExceptionResolver {
    fun get(error: Throwable, strategy: DataSideEffectStrategy, data: Any? = null): DomainException {
        return if (strategy.hasManagedError(error)) {
            strategy.getDomainException(error, data)
        } else {
            proccessError(error)
        }
    }

    private fun proccessError(error: Throwable) = when (error) {
        is SQLiteFullException -> StorageLimitExceededException(error.message.orEmpty())
        is SQLiteDiskIOException -> DiskReadWriteException(error.message.orEmpty())
        is SQLiteException -> GeneralDatabaseException(error.message.orEmpty())
        else -> DomainException(error.message.orEmpty())
    }
}
