package com.alternova.bloodpressure.ui.bloodpressureentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.model.MeasurementState
import com.alternova.bloodpressure.domain.usecase.SaveMeasurementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

sealed interface BloodPressureEntryContract {

    data class State(
        val systolicPressure: Int = 0,
        val diastolicPressure: Int = 0
    )

    sealed interface NavEffect {
        data object NavToBloodPressureList : NavEffect
    }
}

@HiltViewModel
class BloodPressureEntryViewModel @Inject constructor(
    private val saveMeasurementUseCase: SaveMeasurementUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow(BloodPressureEntryContract.State())
    val state = mutableState.asStateFlow()

    private val mutableNavEffect: Channel<BloodPressureEntryContract.NavEffect> = Channel()
    val navEffect = mutableNavEffect.receiveAsFlow()

    fun onSystolicPressureChange(value: Int) {
        mutableState.update { it.copy(systolicPressure = value) }
    }

    fun onDiastolicPressureChange(value: Int) {
        mutableState.update { it.copy(diastolicPressure = value) }
    }

    fun onSaveMeasurement() {
        viewModelScope.launch {
            saveMeasurementUseCase.invoke(
                systolicPressure = state.value.systolicPressure,
                diastolicPressure = state.value.diastolicPressure,
                measurementState = MeasurementState.Rest
            )
            mutableNavEffect.send(BloodPressureEntryContract.NavEffect.NavToBloodPressureList)
        }
    }
}
