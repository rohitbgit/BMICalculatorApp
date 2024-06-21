package com.example.bmicalculator

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine


fun getUserWeightData(): List<Pair<String, Double>> {
    return listOf(
        "2024-06-13" to 70.0,
        "2024-06-14" to 70.5,
        "2024-06-15" to 71.0,
        "2024-06-16" to 70.8,
        "2024-06-17" to 70.6,
        "2024-06-18" to 70.4,
        "2024-06-19" to 70.3
    )
}
@Composable
fun WeightGraph() {
    val steps = 5;
    val weightData = getUserWeightData()
    val points = weightData.mapIndexed{index,data-> Point(index.toFloat(),data.second.toFloat())}
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color(0xFFFFDEAE))
        .steps(points.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color(0xFFFF9494))
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = 100 / steps
            (i * yScale).toString()
        }.build()
    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = points,
                    lineStyle = LineStyle(color = Color.Blue, style = Stroke(width = 1f)),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData= yAxisData,
        backgroundColor = Color.White
        )
    LineChart(modifier = Modifier.fillMaxWidth().height(200.dp), lineChartData =lineChartData )
}

