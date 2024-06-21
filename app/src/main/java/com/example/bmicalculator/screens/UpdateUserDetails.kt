package com.example.bmicalculator.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.yml.charts.common.extensions.isNotNull
import com.example.bmicalculator.AppComponent.HeightWeightInputField
import com.example.bmicalculator.Storage.loadHeightData
import com.example.bmicalculator.Storage.loadWeightData
import com.example.bmicalculator.Storage.saveHeightToStorage
import com.example.bmicalculator.Storage.saveWeightToStorage


@Composable
fun UserSettingsScreen(navController: NavController) {
    val height = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val heightUnit = remember { mutableStateOf("cm") }
    val weightUnit = remember { mutableStateOf("kg") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        height.value = loadHeightData(context)
        weight.value = loadWeightData(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC570))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Update Your Details", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        HeightWeightInputField(
            label = "Height",
            value = height.value,
            unit = heightUnit,
            onUnitChange = {
                heightUnit.value = it
            },
            onValueChange = {
                height.value = it
            })

        Spacer(modifier = Modifier.height(16.dp))

        HeightWeightInputField(
            label = "Weight",
            value = weight.value,
            unit = weightUnit,
            onUnitChange = {
                weightUnit.value = it
            },
            onValueChange = {
                weight.value = it
            })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            saveWeightToStorage(context,weight.value)
            saveHeightToStorage(context,height.value)

        }) {
            Text("Save Changes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("BMIDetailScreen/${height.value}/${weight.value}")
        }) {
            Text("Calculate BMI", fontSize = 18.sp)
        }
    }
}