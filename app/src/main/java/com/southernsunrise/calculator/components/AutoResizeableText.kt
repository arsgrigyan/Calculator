package com.southernsunrise.calculator.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.southernsunrise.calculator.ui.theme.circularStd

@Composable
fun AutoResizeableText(
    modifier: Modifier = Modifier,
    text: String,
    defaultFontSize: TextUnit,
    minFontSize: Float = 10f,
    fontFamily: FontFamily = circularStd,
    fontWeight: FontWeight = FontWeight.Light,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle = MaterialTheme.typography.bodySmall.copy(
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontSize = defaultFontSize,
    ),
    color: Color = style.color,
) {

    var shouldDrawText by remember {
        mutableStateOf(false)
    }
    var resizedTextStyle by remember {
        mutableStateOf(style.copy(fontSize = defaultFontSize, color = color))
    }

    Text(
        modifier = modifier.drawWithContent { if (shouldDrawText) drawContent() },
        text = text,
        color = color,
        softWrap = false,
        style = resizedTextStyle,
        textAlign = textAlign,
        onTextLayout = { result: TextLayoutResult ->

            if ((result.didOverflowHeight || result.didOverflowWidth) && resizedTextStyle.fontSize.value >= minFontSize) {
                resizedTextStyle =
                    resizedTextStyle.copy(
                        textAlign = textAlign,
                        fontSize = resizedTextStyle.fontSize * 0.95
                    )
            } else {
                shouldDrawText = true
            }
        },


        )
}