package com.alternova.bloodpressure.domain.repository

import com.alternova.bloodpressure.data.exception.DataSideEffectStrategy
import com.alternova.bloodpressure.data.exception.ExceptionResolver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

open class BaseRepository {
    suspend fun<T> execute(
        context: CoroutineContext = Dispatchers.IO,
        dataErrorStrategy: DataSideEffectStrategy,
        strategyDataBlock: (suspend() -> T)? = null,
        block: suspend() -> T,
    ): T {
        val handleException by lazy { ExceptionResolver() }
        return withContext(context){
            try {
                block()
            }
            catch (error: Throwable) {
                val data = strategyDataBlock?.invoke()
                throw handleException.get(error, dataErrorStrategy, data)
            }
        }
    }

    fun <T> executeFlow(
        dataErrorStrategy: DataSideEffectStrategy,
        strategyDataBlock: (suspend () -> T)? = null,
        block: () -> Flow<T> // Ojo: ya no es suspend
    ): Flow<T> {
        val handleException by lazy { ExceptionResolver() }
        return block()
            .catch { error ->
                val data = strategyDataBlock?.invoke()
                throw handleException.get(error, dataErrorStrategy, data)
            }
    }
}
