package com.alternova.bloodpressure.ui.bloodpressureentry

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alternova.bloodpressure.domain.model.MeasurementState
import com.alternova.bloodpressure.domain.usecase.GetMeasurementStateUseCase
import com.alternova.bloodpressure.domain.usecase.SaveMeasurementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface BloodPressureEntryContract {

    data class State(
        val systolicPressure: Int = 0,
        val diastolicPressure: Int = 0,
        val selectedState: MeasurementState = MeasurementState.Rest,
        val measurementState: List<MeasurementState> = emptyList()
    )

    sealed interface ViewEffect {
        data object ShowLoadError: ViewEffect
    }

    sealed interface NavEffect {
        data object NavToBloodPressureList : NavEffect
        data object NavToBack : NavEffect
    }
}

@HiltViewModel
class BloodPressureEntryViewModel @Inject constructor(
    private val saveMeasurement: SaveMeasurementUseCase,
    private val getMeasurementState: GetMeasurementStateUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow(BloodPressureEntryContract.State())
    val state = mutableState
        .onStart { fetchMeasurementState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BloodPressureEntryContract.State()
        )

    private val mutableEffect: Channel<BloodPressureEntryContract.ViewEffect> = Channel()
    val effect = mutableEffect.receiveAsFlow()

    private val mutableNavEffect: Channel<BloodPressureEntryContract.NavEffect> = Channel()
    val navEffect = mutableNavEffect.receiveAsFlow()

    fun onSystolicPressureChange(value: Int) {
        mutableState.update { it.copy(systolicPressure = value) }
    }

    fun onDiastolicPressureChange(value: Int) {
        mutableState.update { it.copy(diastolicPressure = value) }
    }

    fun onStateSelected(state: MeasurementState) {
        mutableState.update { it.copy(selectedState = state) }
    }

    fun onSaveMeasurement() {
        viewModelScope.launch {
            runCatching {
                saveMeasurement.invoke(
                    systolicPressure = state.value.systolicPressure,
                    diastolicPressure = state.value.diastolicPressure,
                    measurementState = state.value.selectedState
                )
            }
            .onSuccess {
                mutableNavEffect.send(BloodPressureEntryContract.NavEffect.NavToBloodPressureList)
            }
            .onFailure {
                Log.d("Error Type", it.javaClass.name)
                mutableEffect.send(BloodPressureEntryContract.ViewEffect.ShowLoadError)
            }
        }
    }

    fun onBack() {
        viewModelScope.launch {
            mutableNavEffect.send(BloodPressureEntryContract.NavEffect.NavToBack)
        }
    }

    private fun fetchMeasurementState() {
        mutableState.update { it.copy(measurementState = getMeasurementState.invoke()) }
    }
}
