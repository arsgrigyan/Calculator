package com.southernsunrise.calculator.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.southernsunrise.calculator.utils.CalculatorAction
import com.southernsunrise.calculator.utils.CalculatorOperation
import com.southernsunrise.calculator.utils.CalculatorState
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

class CalculatorViewModel() : ViewModel() {
    var calculatorState by mutableStateOf(CalculatorState())
        private set


    fun invokeCalculatorAction(calculatorAction: CalculatorAction) {
        when (calculatorAction) {
            CalculatorAction.CLEAR -> clearExpressionAndResultTexts()
            CalculatorAction.DECIMALIZATION -> decimalize()
            CalculatorAction.DELETION -> deleteExpressionLastCharacter()
            CalculatorAction.CALCULATION -> onManualCalculation()
            is CalculatorAction.NUMBER_INSERTION -> insertNumber(calculatorAction.symbol)
            is CalculatorAction.OPERATION -> insertOperation(calculatorAction.operation)
        }
    }


    private fun insertOperation(operation: CalculatorOperation) {
        if (calculatorState.firstNumber.isNotBlank() && calculatorState.firstNumber.last() != '.') {
            if (calculatorState.secondNumber.isBlank()) {
                calculatorState = calculatorState.copy(
                    operation = operation,
                    expression = calculatorState.firstNumber + operation.symbol
                )
                if (operation == CalculatorOperation.MODULO) autoCalculate()
            } else {
                // when there is already an operation with two operand numbers
                // if you type another operation  then the previous one gets calculated
                // and the result is set to the first operand number
                onManualCalculation()
                calculatorState = calculatorState.copy(
                    operation = operation,
                    expression = calculatorState.firstNumber + operation.symbol
                )
            }
        } else {
            if (operation == CalculatorOperation.SQRT) {
                calculatorState = calculatorState.copy(
                    firstNumber = "1",
                    operation = operation,
                    expression = operation.symbol
                )
            }
        }
    }

    private fun insertNumber(symbol: String) {
        if (calculatorState.operation == null) {
            calculatorState = calculatorState.copy(
                firstNumber = calculatorState.firstNumber + symbol,
                expression = calculatorState.firstNumber + symbol
            )
        } else {
            calculatorState = calculatorState.copy(
                secondNumber = calculatorState.secondNumber + symbol,
                expression = calculatorState.firstNumber + calculatorState.operation!!.symbol + calculatorState.secondNumber + symbol
            )
            autoCalculate()
        }

    }


    private fun onManualCalculation() {
        calculatorState =
            if (calculatorState.result.toDoubleOrNull() != null) CalculatorState(
                firstNumber = calculatorState.result,
                expression = calculatorState.result
            )
            else CalculatorState()
    }


    private fun autoCalculate() {
        var calculation: Number = 0.0
        val firstNumber: Double? = calculatorState.firstNumber.toDoubleOrNull()
        val secondNumber: Double? = calculatorState.secondNumber.toDoubleOrNull()

        if (firstNumber != null && calculatorState.operation != null) {
            when (calculatorState.operation) {
                CalculatorOperation.ADDITION -> {
                    if (secondNumber != null) calculation = firstNumber + secondNumber
                    else {
                        calculatorState = calculatorState.copy(result = "")
                        return
                    }
                }

                CalculatorOperation.SUBTRACTION -> {
                    if (secondNumber != null) calculation = firstNumber - secondNumber
                    else {
                        calculatorState = calculatorState.copy(result = "")
                        return
                    }

                }

                CalculatorOperation.MULTIPLICATION -> {
                    if (secondNumber != null) calculation = firstNumber * secondNumber
                    else {
                        calculatorState = calculatorState.copy(result = "")
                        return
                    }


                }

                CalculatorOperation.DIVISION -> {
                    if (secondNumber != null) {
                        if (secondNumber != 0.0) {
                            calculation = firstNumber / secondNumber!!
                        } else {
                            calculatorState =
                                calculatorState.copy(result = "Not defined")
                            return
                        }
                    } else {
                        calculatorState = calculatorState.copy(result = "")
                        return

                    }

                }

                CalculatorOperation.MODULO -> {
                    val secondNum: Double = secondNumber ?: 10.0
                    calculation = firstNumber * (secondNum / 100)

                }

                CalculatorOperation.SQRT -> {
                    if (secondNumber != null) calculation = firstNumber * sqrt(secondNumber)
                    else {
                        calculatorState = calculatorState.copy(result = "")
                        return
                    }
                }

                null -> {}
            }

            calculatorState = if (calculation.toString().length > 15) {
                calculatorState.copy(result = String.format("%.2e", calculation))
            } else {
                if (floor(calculation.toDouble()) == calculation && ceil(calculation.toDouble()) == calculation) calculation =
                    calculation.toInt()
                calculatorState.copy(result = calculation.toString())
            }
        }

    }

    private fun deleteExpressionLastCharacter() {
        val firstNumberStr: String = calculatorState.firstNumber
        val secondNumberStr: String = calculatorState.secondNumber

        if (secondNumberStr.isNotBlank()) {
            calculatorState = calculatorState.copy(
                secondNumber = secondNumberStr.dropLast(1),
                expression = calculatorState.expression.dropLast(1)
            )
        } else {
            calculatorState = if (calculatorState.operation != null) {
                calculatorState.copy(
                    operation = null,
                    expression = calculatorState.expression.dropLast(1)
                )
            } else calculatorState.copy(
                firstNumber = firstNumberStr.dropLast(1),
                expression = calculatorState.expression.dropLast(1)
            )
        }

        autoCalculate()
    }

    private fun decimalize() {
        if (calculatorState.operation == null && calculatorState.firstNumber.isNotBlank() && calculatorState.firstNumber.last() != '.') {
            calculatorState = calculatorState.copy(
                firstNumber = calculatorState.firstNumber + ".",
                expression = calculatorState.firstNumber + '.'
            )
        }
        if (calculatorState.operation != null && calculatorState.secondNumber.isNotBlank() && calculatorState.secondNumber.last() != '.') {
            calculatorState =
                calculatorState.copy(
                    secondNumber = calculatorState.secondNumber + ".",
                    expression = calculatorState.firstNumber + calculatorState.operation!!.symbol + calculatorState.secondNumber + '.'
                )
        }
    }

    private fun clearExpressionAndResultTexts() {
        calculatorState = CalculatorState()
    }

}