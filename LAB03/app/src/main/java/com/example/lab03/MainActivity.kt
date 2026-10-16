package com.example.lab03

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "CalculatorDebug" // Tag do filtrowania w Logcat

    private lateinit var tvResult: TextView
    private var currentInput = ""
    private var firstValue = 0.0
    private var currentOperator = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Aplikacja została uruchomiona.")
        tvResult = findViewById(R.id.tvResult)

        // Jawnie zdefiniowana lista przycisków numerycznych z jawnym typem
        val numberButtons: List<Pair<Int, String>> = listOf(
            Pair(R.id.btn0, "0"),
            Pair(R.id.btn1, "1"),
            Pair(R.id.btn2, "2"),
            Pair(R.id.btn3, "3"),
            Pair(R.id.btn4, "4"),
            Pair(R.id.btn5, "5"),
            Pair(R.id.btn6, "6"),
            Pair(R.id.btn7, "7"),
            Pair(R.id.btn8, "8"),
            Pair(R.id.btn9, "9")
        )

        for ((id, digit) in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                if (isNewOperation) {
                    currentInput = ""
                    isNewOperation = false
                }
                currentInput += digit
                tvResult.text = currentInput
                Log.d(TAG, "Kliknięto cyfrę: $digit, aktualny input: $currentInput")
            }
        }

        // Przycisk kropki (.)
        findViewById<Button>(R.id.btnDot).setOnClickListener {
            if (isNewOperation) {
                currentInput = "0"
                isNewOperation = false
            }
            if (!currentInput.contains(".")) {
                if (currentInput.isEmpty()) {
                    currentInput = "0"
                }
                currentInput += "."
                tvResult.text = currentInput
            }
            Log.d(TAG, "Kliknięto kropkę, aktualny input: $currentInput")
        }

        // Przycisk czyszczenia (C)
        findViewById<Button>(R.id.btnC).setOnClickListener {
            currentInput = ""
            firstValue = 0.0
            currentOperator = ""
            tvResult.text = "0"
            isNewOperation = true
            Log.d(TAG, "Wyczyszczono kalkulator (Reset).")
        }

        // Przycisk usuwania jednej cyfry (DEL)
        findViewById<Button>(R.id.btnDEL).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                if (currentInput.isEmpty() || currentInput == "-") {
                    currentInput = ""
                    tvResult.text = "0"
                } else {
                    tvResult.text = currentInput
                }
            }
            Log.d(TAG, "Kliknięto DEL, aktualny input: $currentInput")
        }

        // Przycisk zmiany znaku (+/-)
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener {
            if (currentInput.isNotEmpty() && currentInput != "0") {
                currentInput = if (currentInput.startsWith("-")) {
                    currentInput.substring(1)
                } else {
                    "-$currentInput"
                }
                tvResult.text = currentInput
            }
            Log.d(TAG, "Kliknięto +/- , aktualny input: $currentInput")
        }

        // Przycisk obliczania procentu (%)
        findViewById<Button>(R.id.btnPercent).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                val value = currentInput.toDouble() / 100.0
                tvResult.text = if (value % 1.0 == 0.0) value.toInt().toString() else value.toString()
                currentInput = tvResult.text.toString()
                isNewOperation = true
            }
            Log.d(TAG, "Obliczono procent, wynik: $currentInput")
        }

        // Operatory arytmetyczne (+, -, *, /) z jawnym typem
        val operatorButtons: List<Pair<Int, String>> = listOf(
            Pair(R.id.btnPlus, "+"),
            Pair(R.id.btnMinus, "-"),
            Pair(R.id.btnMultiply, "*"),
            Pair(R.id.btnDivide, "/")
        )

        for ((id, op) in operatorButtons) {
            findViewById<Button>(id).setOnClickListener {
                if (currentInput.isNotEmpty()) {
                    firstValue = currentInput.toDouble()
                    currentOperator = op
                    isNewOperation = true
                    Log.d(TAG, "Wybrano operator: $op, zapamiętana wartość: $firstValue")
                }
            }
        }

        // Przycisk równa się (=)
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
                val secondValue = currentInput.toDouble()
                Log.d(TAG, "Wykonywanie obliczenia: $firstValue $currentOperator $secondValue")

                val result = when (currentOperator) {
                    "+" -> firstValue + secondValue
                    "-" -> firstValue - secondValue
                    "*" -> firstValue * secondValue
                    "/" -> if (secondValue != 0.0) firstValue / secondValue else 0.0
                    else -> 0.0
                }
                tvResult.text = if (result % 1.0 == 0.0) result.toInt().toString() else result.toString()
                Log.d(TAG, "Wynik operacji: ${tvResult.text}")

                currentInput = tvResult.text.toString()
                currentOperator = ""
                isNewOperation = true
            }
        }
    }
}
