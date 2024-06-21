package com.example.bmicalculator.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.bmicalculator.BMIDisplay
import com.example.bmicalculator.WeightGraph
import com.example.bmicalculator.calculateBMI
import com.example.bmicalculator.getBMICategory



@Composable
fun BMIDetailScreen(height: Double, weight: Double,heightUnit :String,weightUnit: String) {
    val convertHeight = if(heightUnit=="m") cmToMeter(height)  else height
    val convertWeight = if(weightUnit=="lb") poundToKg(weight) else weight
    val bmi = calculateBMI(convertHeight, convertWeight)
    val category = getBMICategory(bmi)
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF8F8F8)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "BMI Details", fontSize = 34.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            BMIDisplay(height = convertHeight, weight = convertWeight)

            Spacer(modifier = Modifier.height(16.dp))

            DonutChartDisplay(bmi, category)
            Divider(modifier = Modifier.padding(16.dp).height(2.dp).fillMaxWidth(1f), color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Weight Data for the Past Week", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            WeightGraph()

        }
    }
}

@Composable
fun DonutChartDisplay(bmi: Double, category: String) {
    val donutChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("Underweight", if (category == "Underweight") bmi.toFloat() else 0f, Color(0xFF5F0A87)),
            PieChartData.Slice("Normal weight", if (category == "Normal weight") bmi.toFloat() else 0f, Color(0xFF20BF55)),
            PieChartData.Slice("Overweight", if (category == "Overweight") bmi.toFloat() else 0f, Color(0xFFEC9F05)),
            PieChartData.Slice("Obese", if (category == "Obese") bmi.toFloat() else 0f, Color(0xFFF53844))
        ),
        plotType = PlotType.Donut
    )
    val pieChartConfig = PieChartConfig(
        labelVisible = true,
        showSliceLabels = true,
        animationDuration = 1000,
        labelColor = Color.Black,
    )
    DonutPieChart(
        modifier = Modifier
            .padding(16.dp)
            .height(200.dp),
        pieChartData = donutChartData, pieChartConfig = pieChartConfig
    )
}


