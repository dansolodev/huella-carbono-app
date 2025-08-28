package com.fs.huellacarbonoapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Co2
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.EmojiTransportation
import androidx.compose.material.icons.outlined.Propane
import androidx.compose.material.icons.outlined.WbIncandescent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fs.huellacarbonoapp.R
import com.fs.huellacarbonoapp.ui.components.ItemInputInformation
import com.fs.huellacarbonoapp.ui.theme.HuellaCarbonoAppTheme
import com.fs.huellacarbonoapp.ui.theme.Typography
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.dashboard_label))
                },
                actions = {
                    Icon(imageVector = Icons.Outlined.Eco, contentDescription = null)
                }
            )
        }
    ) { innerPadding ->

        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            var electricityInputValue by remember { mutableStateOf("") }
            var totalElectricityConsumption by remember { mutableStateOf("") }

            var transportInputValue by remember { mutableStateOf("") }
            var transportConsumption by remember { mutableStateOf("") }

            var gasLPInputValue by remember { mutableStateOf("") }
            var gasLPConsumption by remember { mutableStateOf("") }

            AnimatedVisibility(
                visible = totalElectricityConsumption.isNotBlank() &&
                        transportConsumption.isNotBlank() && gasLPConsumption.isNotBlank()
            ) {

                coroutineScope.launch { scrollState.animateScrollTo(0) }

                val totalCO2PerYear =
                    totalElectricityConsumption.toFloat() + transportConsumption.toFloat() + gasLPConsumption.toFloat()
                val strTotalCO2PerYear =
                    String.format(locale = Locale.ROOT, format = "%.02f", totalCO2PerYear)
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Co2,
                        contentDescription = stringResource(R.string.cd_icon_co2),
                        modifier = Modifier.weight(0.15f)
                    )
                    Text(
                        text = stringResource(
                            R.string.total_carbon_footprint_result_per_year,
                            strTotalCO2PerYear
                        ),
                        style = Typography.titleMedium,
                        modifier = Modifier.weight(0.85f),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            ItemInputInformation(
                value = electricityInputValue,
                onValueChange = {
                    electricityInputValue = it
                },
                onSendButtonAction = {
                    totalElectricityConsumption = calculateCarbonFootPrint(
                        amountQuantityCO2 = 0.455f,
                        totalConsumption = it.toFloatOrNull() ?: 0f
                    )
                },
                titleTypeIdRes = R.string.electricity_label,
                labelInputTypeIdRes = R.string.electricity_label_unit,
                iconVectorType = Icons.Outlined.WbIncandescent,
                backgroundColor = Color.Yellow,
                resultCalculated = totalElectricityConsumption
            )


            ItemInputInformation(
                value = transportInputValue,
                onValueChange = {
                    transportInputValue = it
                },
                onSendButtonAction = {
                    transportConsumption = calculateCarbonFootPrintByTransport(
                        totalConsumption = it.toFloatOrNull() ?: 0f
                    )
                },
                titleTypeIdRes = R.string.transport_label,
                labelInputTypeIdRes = R.string.transport_label_unit,
                iconVectorType = Icons.Outlined.EmojiTransportation,
                backgroundColor = Color.Green,
                resultCalculated = transportConsumption
            )

            ItemInputInformation(
                value = gasLPInputValue,
                onValueChange = {
                    gasLPInputValue = it
                },
                onSendButtonAction = {
                    gasLPConsumption = calculateCarbonFootPrint(
                        amountQuantityCO2 = 3.02f,
                        totalConsumption = it.toFloatOrNull() ?: 0f
                    )
                },
                titleTypeIdRes = R.string.gas_label,
                labelInputTypeIdRes = R.string.gas_label_unit,
                iconVectorType = Icons.Outlined.Propane,
                backgroundColor = Color.Blue,
                resultCalculated = gasLPConsumption
            )
            Spacer(modifier = Modifier.height(height = 80.dp))
        }
    }
}

private fun calculateCarbonFootPrint(amountQuantityCO2: Float, totalConsumption: Float): String {
    val totalCO2PerMonth = (totalConsumption / 1) * amountQuantityCO2
    val totalCO2 = totalCO2PerMonth * 12
    return String.format(locale = Locale.ROOT, format = "%.02f", totalCO2)
}

private fun calculateCarbonFootPrintByTransport(totalConsumption: Float): String {
    val totalCO2PerMonth = (1000f / totalConsumption) * 2.31
    val totalCO2 = totalCO2PerMonth * 12
    return String.format(locale = Locale.ROOT, format = "%.02f", totalCO2)
}

@Preview(
    showBackground = true
)
@Composable
private fun DashboardScreenPreview() {
    HuellaCarbonoAppTheme {
        DashboardScreen()
    }
}
