package com.alternova.bloodpressure.data.exception

import android.database.sqlite.SQLiteAccessPermException
import com.alternova.bloodpressure.domain.exception.DomainException
import com.alternova.bloodpressure.domain.exception.GeneralDatabaseException
import javax.inject.Inject

class MeasurementSideEffectStrategy @Inject constructor(): DataSideEffectStrategy {

    override val managedExceptions = listOf<Class<out Throwable>>(
        SQLiteAccessPermException::class.java
    )

    override fun getDomainException(error: Throwable, data: Any?): DomainException {
        return if (hasManagedError(error)) {
            GeneralDatabaseException(error.message.orEmpty(), data)
        } else {
            DomainException(error.message.orEmpty())
        }
    }

    override fun hasManagedError(error: Throwable): Boolean {
        return managedExceptions.contains(error::class.java)
    }
}
