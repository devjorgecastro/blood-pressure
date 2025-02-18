package com.alternova.bloodpressure.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.usecase.GetMeasurementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface BloodPressureListContract {

    data class State(
        val data: List<BloodPressureMeasurement> = emptyList(),
        val isError: Boolean = false,
        val diastolicPressure: Int = 0
    )

    sealed interface ViewEffect {
        data object ShowLoadError: ViewEffect
    }

    sealed interface NavEffect {
        data object NavToBloodPressureEntry : NavEffect
    }
}

@HiltViewModel
class BloodPressureListViewModel @Inject constructor(
    private val getMeasurements: GetMeasurementsUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow(BloodPressureListContract.State())
    val state = mutableState
        .onStart { fetchBloodPressures() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BloodPressureListContract.State()
        )

    private val mutableEffect: Channel<BloodPressureListContract.ViewEffect> = Channel()
    val effect = mutableEffect.receiveAsFlow()

    private val mutableNavEffect: Channel<BloodPressureListContract.NavEffect> = Channel()
    val navEffect = mutableNavEffect.receiveAsFlow()

    fun onNewBloodPressureClick() {
        viewModelScope.launch {
            mutableNavEffect.send(BloodPressureListContract.NavEffect.NavToBloodPressureEntry)
        }
    }

    private fun fetchBloodPressures() {
        viewModelScope.launch {
            getMeasurements.invoke()
                .catch {
                    Log.d("Error Type", it.javaClass.name)
                    mutableEffect.send(BloodPressureListContract.ViewEffect.ShowLoadError)
                }
                .collect { data ->
                    mutableState.update { it.copy(data = data) }
                }
        }
    }
}
