package com.southernsunrise.calculator.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.southernsunrise.calculator.ui.theme.calculationResultTextDefaultFontSize
import com.southernsunrise.calculator.ui.theme.circularStd
import com.southernsunrise.calculator.ui.theme.darkGray
import com.southernsunrise.calculator.utils.CalculatorAction
import com.southernsunrise.calculator.utils.CalculatorButtonType
import com.southernsunrise.calculator.utils.CalculatorOperation
import com.southernsunrise.calculator.utils.CalculatorState


import kotlinx.coroutines.delay

@PreviewScreenSizes
@Preview
@Composable
fun CalculatorPreview() {
    Calculator()
}

@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    calculatorState: CalculatorState = CalculatorState(),
    onButtonClickAction: (CalculatorAction) -> Unit = {},
    onButtonLongClickAction: (CalculatorAction) -> Unit = {}
) {
    Box(modifier = modifier) {
        CalculatorResultBar(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .padding(horizontal = 5.dp)
                .align(Alignment.TopCenter)
                .background(Color.Transparent),
            expressionText = calculatorState.expression,
            resultText = calculatorState.result
        )
        CalculatorKeyboard(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .fillMaxHeight(0.55f)
                .align(Alignment.BottomCenter),
            onButtonClickAction = onButtonClickAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorResultBar(
    modifier: Modifier,
    expressionText: String,
    resultText: String
) {
    Column(modifier = modifier) {

        val textFieldFocusRequester = remember { FocusRequester() }
        LaunchedEffect(key1 = Unit) {
            // adding delay before requesting focus on the ResizableInputField() every time the Calculator composable recomposes
            // it works pretty okay with the delay on the split mode and on the pop up view even while fastly changing pop up view size
            delay(600)
            textFieldFocusRequester.requestFocus()
        }

        CompositionLocalProvider(
            LocalTextInputService provides null
        ) {
            ResizableInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                value = expressionText,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(
                    textAlign = TextAlign.End,
                    fontFamily = circularStd,
                    fontWeight = FontWeight.Light,
                    fontSize = 70.sp,
                ),
                focusRequester = textFieldFocusRequester,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = darkGray
                ),
            )

        }
        AutoResizeableText(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
                .align(Alignment.End)
                .background(Color.Transparent),
            text = resultText,
            textAlign = TextAlign.End,
            defaultFontSize = calculationResultTextDefaultFontSize,
            color = darkGray.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun CalculatorKeyboard(
    modifier: Modifier = Modifier,
    onButtonClickAction: (CalculatorAction) -> Unit,
    onButtonLongClickAction: (CalculatorAction) -> Unit = {}
) {
    Column(
        modifier = modifier,
    ) {
        Row(modifier = Modifier.weight(0.8f)) {
            CalculatorButton(
                symbol = "C",
                type = CalculatorButtonType.CLEAR,
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.CLEAR) }
            )
            CalculatorButton(
                symbol = CalculatorOperation.SQRT.symbol,
                modifier = Modifier.weight(1f),
                type = CalculatorButtonType.ADVANCED_OPERATOR,
                onClick = { onButtonClickAction(CalculatorAction.OPERATION(CalculatorOperation.SQRT)) }
            )
            CalculatorButton(
                symbol = CalculatorOperation.MODULO.symbol,
                modifier = Modifier.weight(1f),
                type = CalculatorButtonType.ADVANCED_OPERATOR,
                onClick = { onButtonClickAction(CalculatorAction.OPERATION(CalculatorOperation.MODULO)) }
            )
            CalculatorButton(
                symbol = "◀",
                modifier = Modifier.weight(1.5f),
                type = CalculatorButtonType.DELETE,
                onClick = { onButtonClickAction(CalculatorAction.DELETION) }
            )

        }
        Row(modifier = Modifier.weight(1f)) {
            CalculatorButton(
                symbol = "7",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("7")) }
            )
            CalculatorButton(
                symbol = "8",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("8")) }
            )
            CalculatorButton(
                symbol = "9",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("9")) }
            )
            CalculatorButton(
                symbol = CalculatorOperation.DIVISION.symbol,
                modifier = Modifier.weight(1.5f),
                type = CalculatorButtonType.BASIC_OPERATOR,
                onClick = { onButtonClickAction(CalculatorAction.OPERATION(CalculatorOperation.DIVISION)) }
            )

        }
        Row(modifier = Modifier.weight(1f)) {
            CalculatorButton(
                symbol = "4",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("4")) }
            )
            CalculatorButton(
                symbol = "5",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("5")) }
            )
            CalculatorButton(
                symbol = "6",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("6")) }
            )
            CalculatorButton(
                symbol = CalculatorOperation.MULTIPLICATION.symbol,
                modifier = Modifier.weight(1.5f),
                type = CalculatorButtonType.BASIC_OPERATOR,
                onClick = { onButtonClickAction(CalculatorAction.OPERATION(CalculatorOperation.MULTIPLICATION)) }
            )

        }
        Row(modifier = Modifier.weight(1f)) {
            CalculatorButton(
                symbol = "1",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("1")) }

            )
            CalculatorButton(
                symbol = "2",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("2")) }

            )
            CalculatorButton(
                symbol = "3",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("3")) }

            )
            CalculatorButton(
                symbol = CalculatorOperation.SUBTRACTION.symbol,
                modifier = Modifier.weight(1.5f),
                type = CalculatorButtonType.BASIC_OPERATOR,
                onClick = { onButtonClickAction(CalculatorAction.OPERATION(CalculatorOperation.SUBTRACTION)) }


            )

        }
        Row(modifier = Modifier.weight(1f)) {
            CalculatorButton(
                symbol = "0",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.NUMBER_INSERTION("0")) }
            )
            CalculatorButton(
                symbol = ".",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.DECIMALIZATION) }

            )
            CalculatorButton(
                symbol = "=",
                modifier = Modifier.weight(1f),
                onClick = { onButtonClickAction(CalculatorAction.CALCULATION) }

            )
            CalculatorButton(
                symbol = CalculatorOperation.ADDITION.symbol,
                modifier = Modifier.weight(1.5f),
                type = CalculatorButtonType.BASIC_OPERATOR,
                onClick = { onButtonClickAction(CalculatorAction.OPERATION(CalculatorOperation.ADDITION)) }

            )

        }
    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    symbol: String,
    type: CalculatorButtonType = CalculatorButtonType.NUMBER,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {

    Button(
        modifier = modifier.fillMaxSize(),
        colors = ButtonDefaults.buttonColors(containerColor = type.backgroundColor),
        shape = CutCornerShape(0f),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                ),
            contentAlignment = Alignment.Center
        ) {
            AutoResizeableText(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.7f),
                textAlign = TextAlign.Center,
                text = symbol,
                color = type.textColor,
                defaultFontSize = type.symbolFontSize,
            )
        }

    }

}




