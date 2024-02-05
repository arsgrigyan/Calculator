package com.southernsunrise.calculator.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.southernsunrise.calculator.ui.theme.calculatorButtonDefaultSymbolFontSize
import com.southernsunrise.calculator.ui.theme.darkGray
import com.southernsunrise.calculator.ui.theme.lightGray
import com.southernsunrise.calculator.ui.theme.veryDarkGray
import com.southernsunrise.calculator.ui.theme.veryLightGray


enum class CalculatorButtonType(
    val backgroundColor: Color,
    val textColor: Color = Color.White,
    val symbolFontSize: TextUnit = calculatorButtonDefaultSymbolFontSize
) {
    NUMBER(backgroundColor = veryDarkGray),
    BASIC_OPERATOR(backgroundColor = darkGray),
    ADVANCED_OPERATOR(backgroundColor = veryLightGray, textColor = veryDarkGray),
    CLEAR(backgroundColor = veryLightGray, textColor = veryDarkGray),
    DELETE(backgroundColor = lightGray)
}



