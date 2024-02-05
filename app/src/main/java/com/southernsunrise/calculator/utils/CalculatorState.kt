package com.southernsunrise.calculator.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

data class CalculatorState(
    val firstNumber: String = "",
    val secondNumber: String = "",
    val operation: CalculatorOperation? = null,
    val expression: String = "",
    val result: String = ""
)
