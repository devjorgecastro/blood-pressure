@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.alternova.bloodpressure.ui.bloodpressureentry

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alternova.bloodpressure.FAB_EXPLODE_BOUNDS_KEY

@Composable
fun SharedTransitionScope.BloodPressureEntryScreen(
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    var systolicPressure by remember { mutableStateOf("") }
    var diastolicPressure by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .sharedBounds(
                sharedContentState = rememberSharedContentState(
                    key = FAB_EXPLODE_BOUNDS_KEY
                ),
                animatedVisibilityScope = animatedVisibilityScope
            )
    ) {
        BloodPressureInput(
            systolicValue = systolicPressure,
            onSystolicValueChange = { systolicPressure = it },
            diastolicValue = diastolicPressure,
            onDiastolicValueChange = { diastolicPressure = it }
        )
    }
}

@Composable
fun BloodPressureInput(
    systolicValue: String,
    onSystolicValueChange: (String) -> Unit,
    diastolicValue: String,
    onDiastolicValueChange: (String) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TextField for systolic pressure
            OutlinedTextField(
                value = systolicValue,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                        onSystolicValueChange(newValue)
                    }
                },
                label = { Text("Systolic pressure") },
                suffix = { Text("mmHg") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // TextField for diastolic pressure
            OutlinedTextField(
                value = diastolicValue,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                        onDiastolicValueChange(newValue)
                    }
                },
                label = { Text("Diastolic pressure") },
                suffix = { Text("mmHg") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {}
            ) {
                Text("Save")
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun BloodPressureEntryScreenPreview() {
    BloodPressureEntryScreen()
}
*/