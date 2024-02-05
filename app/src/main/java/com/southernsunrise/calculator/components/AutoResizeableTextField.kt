package com.southernsunrise.calculator.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.unit.TextUnit

@Composable
fun ResizableInputField(
    modifier: Modifier = Modifier,
    value: String,
    keyboardOptions: KeyboardOptions,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {

    BoxWithConstraints {
        TextField(
            modifier = modifier
                .focusRequester(focusRequester)
                .onGloballyPositioned { focusRequester.requestFocus() },
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = textStyle.copy(fontSize = calculateShrunkFontSize(value)),
            keyboardOptions = keyboardOptions,
            colors = colors,
            enabled = true,
        )
    }
}


@Composable
private fun BoxWithConstraintsScope.calculateShrunkFontSize(text: String): TextUnit {
    var shrunkFontSize = MaterialTheme.typography.displayMedium.fontSize
    val calculateIntrinsics: @Composable () -> ParagraphIntrinsics = {
        ParagraphIntrinsics(
            text = text,
            style = MaterialTheme.typography.displayMedium.copy(fontSize = shrunkFontSize),
            density = LocalDensity.current,
            fontFamilyResolver = createFontFamilyResolver(LocalContext.current)
        )
    }

    var intrinsics = calculateIntrinsics()
    with(LocalDensity.current) {
        while (intrinsics.maxIntrinsicWidth > maxWidth.toPx()) {
            shrunkFontSize *= 0.9f
            intrinsics = calculateIntrinsics()
        }
    }

    return shrunkFontSize
}