package com.android.unitconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UnitConverterUI() {

    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }

    var inputFactor by remember { mutableDoubleStateOf(1.0) }
    var outputFactor by remember { mutableDoubleStateOf(1.0) }

    var inputexpanded by remember { mutableStateOf(false) }
    var outputexpanded by remember { mutableStateOf(false) }

    fun convertUnits() {
        val inputValue = input.toDoubleOrNull() ?: 0.0
        val result = inputValue * inputFactor / outputFactor
        output = result.toString()
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Unit Converter",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = input,
                        onValueChange = {
                            input = it
                            convertUnits()
                        },
                        placeholder = { Text(text = "Enter a value") },
                        label = { Text(text = "Value") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DropDownButton(
                            label = inputUnit,
                            expanded = inputexpanded,
                            onExpandedChange = { inputexpanded = it },
                            onOptionSelected = { unit, factor ->
                                inputUnit = unit
                                inputFactor = factor
                                convertUnits()
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        DropDownButton(
                            label = outputUnit,
                            expanded = outputexpanded,
                            onExpandedChange = { outputexpanded = it },
                            onOptionSelected = { unit, factor ->
                                outputUnit = unit
                                outputFactor = factor
                                convertUnits()
                            }
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = "Output: $output $outputUnit",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun DropDownButton(
    label: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (String, Double) -> Unit
) {
    Box {
        Button(
            onClick = { onExpandedChange(!expanded) },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text = label)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(if (expanded) 180f else 0f)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            listOf(
                "Meters" to 1.0,
                "Kilometers" to 1000.0,
                "Centimeters" to 0.01,
                "Feet" to 0.3048,
            ).forEach { (unit, factor) ->
                DropdownMenuItem(
                    text = { Text(text = unit) },
                    onClick = {
                        onOptionSelected(unit, factor)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}
