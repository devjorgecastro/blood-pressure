package com.alternova.bloodpressure.domain.usecase

import com.alternova.bloodpressure.data.repository.LocalMeasurementRepository
import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.model.MeasurementState
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SaveMeasurementUseCaseTest {

    @MockK
    private lateinit var repository: LocalMeasurementRepository
    private lateinit var usecase: SaveMeasurementUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        usecase = SaveMeasurementUseCase(repository)
    }

    @Test
    fun `Given a measurement data, When user wants to save, Then it should be saved`() = runTest {
        // Given
        val inputSlot = slot<BloodPressureMeasurement>()
        coEvery { repository.saveMeasurement(capture(inputSlot))  } just Runs

        // When
        usecase.invoke(
            systolicPressure = 90,
            diastolicPressure = 120,
            measurementState = MeasurementState.LightActivity
        )

        // Then
        coVerify(exactly = 1) {
            repository.saveMeasurement(any())
        }

        assertEquals(90, inputSlot.captured.systolic)
        assertEquals(120, inputSlot.captured.diastolic)
        assertEquals(MeasurementState.LightActivity, inputSlot.captured.state)
        confirmVerified(repository)
    }
}
