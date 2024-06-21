package com.example.bmicalculator.AppComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.example.bmicalculator.screens.cmToMeter
import com.example.bmicalculator.screens.kgToPound
import com.example.bmicalculator.screens.meterToCm
import com.example.bmicalculator.screens.poundToKg

@Composable
fun HeightWeightInputField(
    label: String,
    value: String,
    unit: MutableState<String>,
    onUnitChange: (String) -> Unit,
    onValueChange: (String) -> Unit
) {
    val previousUnit = remember { mutableStateOf(unit.value) }

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "$label (${unit.value})") }
        )
        Row {
            val units = if (label == "Height") listOf("cm", "m") else listOf("kg", "lb")
            units.forEach { text ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = unit.value == text,
                        onClick = {
                            val oldUnit = unit.value
                            onUnitChange(text)
                            if (value.isNotBlank()) {
                                val newValue = when (label) {
                                    "Height" -> {
                                        if (oldUnit == "cm" && text == "m") cmToMeter(value.toDouble())
                                        else if (oldUnit == "m" && text == "cm") meterToCm(value.toDouble())
                                        else value.toDouble()
                                    }

                                    "Weight" -> {
                                        if (oldUnit == "kg" && text == "lb") kgToPound(value.toDouble())
                                        else if (oldUnit == "lb" && text == "kg") poundToKg(value.toDouble())
                                        else value.toDouble()
                                    }

                                    else -> value.toDouble()
                                }
                                onValueChange(newValue.toString())
                            }
                            previousUnit.value = text
                        }
                    )
                    Text(text = text)
                }
            }
        }
    }
}
