package com.alternova.bloodpressure.domain.usecase

import com.alternova.bloodpressure.data.repository.LocalMeasurementRepository
import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMeasurementsUseCaseTest {

    @MockK
    private lateinit var repository: LocalMeasurementRepository
    private lateinit var usecase: GetMeasurementsUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        usecase = GetMeasurementsUseCase(repository)
    }

    @Test
    fun `Given the user wants to get measurements, When the data is available, Then it should be returned`() = runTest {
        // Given
        val data = mockk<BloodPressureMeasurement>()
        coEvery { repository.getMeasurements()  } returns flowOf(listOf(data))

        // When
        usecase.invoke().collect {
            assert(it.isNotEmpty())
        }

        // Then
        coVerify(exactly = 1) {
            repository.getMeasurements()
        }

        confirmVerified(repository)
    }
}
