package com.alternova.bloodpressure.ui.bloodpressureentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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

@Composable
fun BloodPressureEntryScreen() {
    var systolicPressure by remember { mutableStateOf("") }
    var diastolicPressure by remember { mutableStateOf("") }

    BloodPressureInput(
        systolicValue = systolicPressure,
        onSystolicValueChange = { systolicPressure = it },
        diastolicValue = diastolicPressure,
        onDiastolicValueChange = { diastolicPressure = it },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun BloodPressureInput(
    systolicValue: String,
    onSystolicValueChange: (String) -> Unit,
    diastolicValue: String,
    onDiastolicValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
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

@Preview(showBackground = true)
@Composable
fun BloodPressureEntryScreenPreview() {
    BloodPressureEntryScreen()
}