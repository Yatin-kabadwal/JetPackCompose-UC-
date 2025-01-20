package com.example.unitconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UnitConvertUi() {

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }


    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }

    var inputConversionFactor by remember { mutableStateOf(1.0) }
    var outputConversionFactor by remember { mutableStateOf(1.0) }

    var isInputExpanded by remember { mutableStateOf(false) }
    var isOutputExpanded by remember { mutableStateOf(false) }



    fun convertUnits() {
        val input = inputValue.toDoubleOrNull() ?: 0.0
        val result = ((input * inputConversionFactor/outputConversionFactor)*100).roundToInt()/100
        outputValue = result.toString()

    }


    Column (
        modifier = Modifier.fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = {
                Text(text = "Enter Value")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            DropdownButton(
                label = inputUnit,
                expanded = isInputExpanded,
                onExpandChange = {
                    isInputExpanded = !isInputExpanded
                },
                onOptionSelected = { unit, factor ->
                    inputUnit = unit
                    inputConversionFactor = factor
                    convertUnits()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DropdownButton(
                label = outputUnit,
                expanded = isOutputExpanded,
                onExpandChange = {
                    isOutputExpanded = !isOutputExpanded
                    },
                onOptionSelected = { unit, factor ->
                    outputUnit = unit
                    outputConversionFactor = factor
                    convertUnits()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Result: $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun DropdownButton(
    label: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onOptionSelected: (String, Double) -> Unit,
) {
    Box {
        Button(
            onClick = {
                onExpandChange(!expanded) // Toggle the expanded state correctly
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text = label)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(if (expanded) 180f else 0f)
            )
        }

        // Dropdown items
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange(false) } // Close the dropdown when dismissed
        ) {
            listOf(
                "Meters" to 1.0,
                "Kilometers" to 0.001,
                "Centimeters" to 100.0,
                "Feet" to 3.281,
                "Miles" to 0.000621458,
                "Millimeters" to 1000.0,
                "Inch" to 0.0254,
                "Yard" to 0.9144,
                "Decimeters" to 10.0,
                "Nanometers" to 1000000000.0,
                "Micrometers" to 1000000.0
            ).forEach { (unit, factor) ->
                DropdownMenuItem(
                    text = {
                        Text(text = unit)
                    },
                    onClick = {
                        onOptionSelected(unit, factor) // Update the selected unit
                        onExpandChange(false)          // Close the dropdown after selection
                    }
                )
            }
        }
    }
}

