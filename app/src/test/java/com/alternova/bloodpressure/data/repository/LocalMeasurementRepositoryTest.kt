package com.alternova.bloodpressure.data.repository

import com.alternova.bloodpressure.data.exception.MeasurementSideEffectStrategy
import com.alternova.bloodpressure.data.local.db.dao.MeasurementDao
import com.alternova.bloodpressure.data.local.db.entity.BloodPressureMeasurementEntity
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocalMeasurementRepositoryTest {
    @MockK
    private lateinit var dao: MeasurementDao
    private lateinit var repository: LocalMeasurementRepository

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        repository = LocalMeasurementRepository(
            dao = dao,
            dataSideEffectStrategy = MeasurementSideEffectStrategy()
        )
    }

    @Test
    fun `Given user wants to save a blood pressure measurement, When saveMeasurement is called, Then the measurement is saved`() = runTest {
        val inputSlot = slot<BloodPressureMeasurementEntity>()
        coEvery {  dao.insert(capture(inputSlot)) } just Runs

        repository.saveMeasurement(
            BloodPressureMeasurement(
                systolic = 120,
                diastolic = 80,
                state = MeasurementState.Rest,
                date = "14-02-2024 15:30:32"
            )
        )

        coVerify(exactly = 1) {
            dao.insert(any())
        }

        assertEquals(120, inputSlot.captured.systolic)
        assertEquals(80, inputSlot.captured.diastolic)
        assertEquals(MeasurementState.Rest.toString(), inputSlot.captured.state)
        assertEquals("14-02-2024 15:30:32", inputSlot.captured.date)
        confirmVerified(dao)
    }

    @Test
    fun `Given user wants to get all blood pressure measurements, When getMeasurements is called, Then the measurements are returned`() = runTest {

        val response = listOf(
            BloodPressureMeasurementEntity(
                systolic = 120,
                diastolic = 80,
                state = "Rest",
                date = "14-02-2024 15:30:32"
            )
        )
        coEvery {  dao.getAll() } returns flowOf(response)

        repository.getMeasurements()
            .collect {
                assertEquals(120, it.first().systolic)
                assertEquals(80, it.first().diastolic)
                assertEquals(MeasurementState.Rest, it.first().state)
                assertEquals("14-02-2024 15:30:32", it.first().date)
            }

        coVerify(exactly = 1) {
            dao.getAll()
        }

        confirmVerified(dao)
    }
}