package com.alternova.bloodpressure.domain.usecase

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMeasurementStateUseCaseTest {
    @MockK
    private lateinit var usecase: GetMeasurementStateUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        usecase = GetMeasurementStateUseCase()
    }

    @Test
    fun `Given a measurement data, When user wants to save, Then it should be saved`() = runTest {
        val response = usecase()

        assert(response.isNotEmpty())
        assertEquals(3, response.count())
    }
}