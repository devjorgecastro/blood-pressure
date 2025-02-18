@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.alternova.bloodpressure.ui.bloodpressureentry

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alternova.bloodpressure.FAB_EXPLODE_BOUNDS_KEY
import com.alternova.bloodpressure.R
import com.alternova.bloodpressure.common.compose.CollectEffect
import com.alternova.bloodpressure.domain.model.MeasurementState
import com.alternova.bloodpressure.ui.theme.bloodPressureContainerColor

@Composable
fun SharedTransitionScope.BloodPressureEntryScreen(
    viewModel: BloodPressureEntryViewModel,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val context = LocalContext.current
    val errorMessage = stringResource(R.string.error_recording_blood_pressure)
    val state = viewModel.state.collectAsStateWithLifecycle()

    CollectEffect(viewModel.effect) {
        when (it) {
            BloodPressureEntryContract.ViewEffect.ShowLoadError -> {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    BloodPressureEntryScreen(
        state.value,
        onSystolicPressureChange = viewModel::onSystolicPressureChange,
        onDiastolicPressureChange = viewModel::onDiastolicPressureChange,
        onSaveBloodPressure = viewModel::onSaveMeasurement,
        onStateSelected = viewModel::onStateSelected,
        onBack = viewModel::onBack,
        modifier = Modifier
            .fillMaxSize()
            .sharedBounds(
                sharedContentState = rememberSharedContentState(
                    key = FAB_EXPLODE_BOUNDS_KEY
                ),
                animatedVisibilityScope = animatedVisibilityScope
            )
    )
}

@Composable
private fun BloodPressureEntryScreen(
    state: BloodPressureEntryContract.State,
    modifier: Modifier = Modifier,
    onSystolicPressureChange: (Int) -> Unit = {},
    onDiastolicPressureChange: (Int) -> Unit = {},
    onSaveBloodPressure: () -> Unit = {},
    onStateSelected: (MeasurementState) -> Unit = {},
    onBack: () -> Unit = {}
) {

    var systolicPressure by remember { mutableStateOf("") }
    var diastolicPressure by remember { mutableStateOf("") }

    val isDropDownExpanded = remember { mutableStateOf(false) }
    val itemPosition = remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.bloodPressureContainerColor,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable { onBack() }
                        )
                    },
                    title = {
                        Text(
                            text = stringResource(R.string.enter_your_blood_pressure)
                        )
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.bloodPressureContainerColor
        ) { innerPadding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // TextField for systolic pressure
                    OutlinedTextField(
                        value = systolicPressure,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                systolicPressure = newValue
                                onSystolicPressureChange(newValue.toIntOrNull() ?: 0)
                            }
                        },
                        label = { Text(text = stringResource(R.string.label_systolic_pressure)) },
                        suffix = { Text("mmHg") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    // TextField for diastolic pressure
                    OutlinedTextField(
                        value = diastolicPressure,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                diastolicPressure = newValue
                                onDiastolicPressureChange(newValue.toIntOrNull() ?: 0)
                            }
                        },
                        label = { Text(text = stringResource(R.string.label_diastolic_pressure)) },
                        suffix = { Text("mmHg") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    if (state.measurementState.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { isDropDownExpanded.value = true }
                        ) {

                            Text(
                                text = stringResource(R.string.label_state),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(text = state.measurementState[itemPosition.intValue].description)
                            Image(
                                painter = painterResource(id = R.drawable.arrow_drop_down_24),
                                contentDescription = null
                            )

                            DropdownMenu(
                                expanded = isDropDownExpanded.value,
                                onDismissRequest = {
                                    isDropDownExpanded.value = false
                                }) {
                                state.measurementState.forEachIndexed { index, value ->
                                    DropdownMenuItem(text = {
                                        Text(text = value.description)
                                    },
                                        onClick = {
                                            isDropDownExpanded.value = false
                                            itemPosition.intValue = index
                                            onStateSelected(state.measurementState[index])
                                        })
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { onSaveBloodPressure() }
                    ) {
                        Text(text = stringResource(R.string.label_save))
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun BloodPressureEntryScreenPreview() {
    BloodPressureEntryScreen(
        state = BloodPressureEntryContract.State()
    )
}
