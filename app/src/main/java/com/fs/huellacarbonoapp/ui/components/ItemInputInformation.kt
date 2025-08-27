package com.fs.huellacarbonoapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.WbIncandescent
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fs.huellacarbonoapp.R
import com.fs.huellacarbonoapp.ui.theme.HuellaCarbonoAppTheme
import com.fs.huellacarbonoapp.ui.theme.Typography

@Composable
fun ItemInputInformation(
    value: String,
    onValueChange: (String) -> Unit,
    onSendButtonAction: (String) -> Unit,
    @StringRes titleTypeIdRes: Int,
    @StringRes labelInputTypeIdRes: Int,
    iconVectorType: ImageVector,
    modifier: Modifier = Modifier,
    resultCalculated: String? = null
) {
    val typeString = stringResource(titleTypeIdRes)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row {
            Text(text = typeString, style = Typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = iconVectorType, contentDescription = typeString)
        }
        Text(text = stringResource(R.string.consumption_question, typeString))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(labelInputTypeIdRes))
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (value.isNotBlank()) {
                            onSendButtonAction(value)
                        }
                    }
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                }
            }
        )
        AnimatedVisibility(resultCalculated.isNullOrBlank().not()) {
            Text(
                text = stringResource(
                    R.string.consumption_carbon_footprint_result,
                    typeString,
                    resultCalculated.orEmpty()
                ),
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemInputInformationRowPreview() {
    HuellaCarbonoAppTheme {
        ItemInputInformation(
            value = "",
            onValueChange = {},
            onSendButtonAction = {},
            titleTypeIdRes = R.string.electricity_label,
            labelInputTypeIdRes = R.string.electricity_label_unit,
            iconVectorType = Icons.Outlined.WbIncandescent,
            resultCalculated = "0.455"
        )
    }
}
