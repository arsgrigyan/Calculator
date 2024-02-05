package com.southernsunrise.calculator.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.southernsunrise.calculator.components.Calculator
import com.southernsunrise.calculator.ui.theme.CalculatorTheme
import com.southernsunrise.calculator.ui.theme.veryLightPink

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val calculatorViewModel = viewModel<CalculatorViewModel>()
                    Calculator(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(veryLightPink),
                        calculatorState = calculatorViewModel.calculatorState,
                        onButtonClickAction = calculatorViewModel::invokeCalculatorAction
                    )
                }
            }
        }
    }
}
