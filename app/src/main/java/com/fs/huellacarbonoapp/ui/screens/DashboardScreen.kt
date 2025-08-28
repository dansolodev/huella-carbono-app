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
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.fs.huellacarbonoapp.ui.screens.DashboardScreenConstants.EMPTY_STRING
import com.fs.huellacarbonoapp.ui.screens.DashboardScreenConstants.SCROLL_TOP_START_POSITION
import com.fs.huellacarbonoapp.ui.screens.DashboardScreenConstants.ZERO_DOUBLE
import com.fs.huellacarbonoapp.ui.theme.HuellaCarbonoAppTheme
import com.fs.huellacarbonoapp.ui.theme.Typography
import com.fs.huellacarbonoapp.utils.electricityEmissionsKgCO2e
import com.fs.huellacarbonoapp.utils.formatWithTwoDecimalsToString
import com.fs.huellacarbonoapp.utils.gasolineEmissionsFromKmKgCO2e
import com.fs.huellacarbonoapp.utils.lpgEmissionsKgCO2e
import com.fs.huellacarbonoapp.utils.monthlyToAnnual
import com.fs.huellacarbonoapp.utils.orZero
import kotlinx.coroutines.launch

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

            var electricityInputValue by remember { mutableStateOf(value = EMPTY_STRING) }
            var totalElectricityConsumption by remember { mutableDoubleStateOf(value = ZERO_DOUBLE) }

            var transportInputValue by remember { mutableStateOf(value = EMPTY_STRING) }
            var transportConsumption by remember { mutableDoubleStateOf(value = ZERO_DOUBLE) }

            var gasLPInputValue by remember { mutableStateOf(value = EMPTY_STRING) }
            var gasLPConsumption by remember { mutableDoubleStateOf(value = ZERO_DOUBLE) }

            AnimatedVisibility(
                visible = totalElectricityConsumption != ZERO_DOUBLE &&
                        transportConsumption != ZERO_DOUBLE && gasLPConsumption != ZERO_DOUBLE
            ) {

                coroutineScope.launch { scrollState.animateScrollTo(value = SCROLL_TOP_START_POSITION) }

                val totalCO2 = totalElectricityConsumption + transportConsumption + gasLPConsumption

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
                            totalCO2.monthlyToAnnual().formatWithTwoDecimalsToString()
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
                    totalElectricityConsumption = electricityEmissionsKgCO2e(
                        kWh = it.toDoubleOrNull().orZero()
                    )
                },
                titleTypeIdRes = R.string.electricity_label,
                labelInputTypeIdRes = R.string.electricity_label_unit,
                iconVectorType = Icons.Outlined.WbIncandescent,
                backgroundColor = Color.Yellow,
                resultCalculated = if (totalElectricityConsumption != ZERO_DOUBLE) {
                    totalElectricityConsumption.formatWithTwoDecimalsToString()
                } else {
                    null
                }
            )

            ItemInputInformation(
                value = transportInputValue,
                onValueChange = {
                    transportInputValue = it
                },
                onSendButtonAction = {
                    transportConsumption = gasolineEmissionsFromKmKgCO2e(
                        km = it.toDoubleOrNull().orZero()
                    )
                },
                titleTypeIdRes = R.string.transport_label,
                labelInputTypeIdRes = R.string.transport_label_unit,
                iconVectorType = Icons.Outlined.EmojiTransportation,
                backgroundColor = Color.Green,
                resultCalculated = if (transportConsumption != ZERO_DOUBLE) {
                    transportConsumption.formatWithTwoDecimalsToString()
                } else {
                    null
                }
            )

            ItemInputInformation(
                value = gasLPInputValue,
                onValueChange = {
                    gasLPInputValue = it
                },
                onSendButtonAction = {
                    gasLPConsumption = lpgEmissionsKgCO2e(
                        kgLpg = it.toDoubleOrNull().orZero()
                    )
                },
                titleTypeIdRes = R.string.gas_label,
                labelInputTypeIdRes = R.string.gas_label_unit,
                iconVectorType = Icons.Outlined.Propane,
                backgroundColor = Color.Blue,
                resultCalculated = if (gasLPConsumption != ZERO_DOUBLE) {
                    gasLPConsumption.formatWithTwoDecimalsToString()
                } else {
                    null
                }
            )
            Spacer(modifier = Modifier.height(height = 80.dp))
        }
    }
}

internal object DashboardScreenConstants {
    const val SCROLL_TOP_START_POSITION = 0
    const val ZERO_DOUBLE = 0.0
    const val EMPTY_STRING = ""
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
