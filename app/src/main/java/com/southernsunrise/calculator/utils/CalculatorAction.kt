package com.southernsunrise.calculator.utils

sealed class CalculatorAction() {
    object CLEAR : CalculatorAction()
    object DELETION : CalculatorAction()
    object DECIMALIZATION : CalculatorAction()
    object CALCULATION : CalculatorAction()
    data class OPERATION(val operation: CalculatorOperation) : CalculatorAction()
    data class NUMBER_INSERTION(val symbol: String) : CalculatorAction()

}