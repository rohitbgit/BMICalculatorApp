package com.example.bmicalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.roundTwoDecimal

fun calculateBMI(height: Double, weight: Double): Double {
    val heightInMeters = height / 100
    return if (heightInMeters > 0) {
        weight / (heightInMeters * heightInMeters)
    } else {
        0.0
    }
}

fun getBMICategory(bmi: Double): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi in 18.5..24.9 -> "Normal weight"
        bmi in 25.0..29.9 -> "Overweight"
        else -> "Obese"
    }
}

@Composable
fun BMIDisplay(height: Double, weight: Double) {
    val bmi = calculateBMI(height, weight)
    val category = getBMICategory(bmi)

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("BMI : ", fontSize = 20.sp)
            Text(
                "${bmi.roundTwoDecimal()}",
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Category : ", fontSize = 20.sp)
            Text(
                category, fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}