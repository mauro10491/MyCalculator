package com.example.myfirstcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstcalculator.ui.theme.MyFirstCalculatorTheme
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculator()
        }
    }

    @Composable
    fun Calculator(modifier: Modifier = Modifier){
        var input by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }
        var hasCalculated by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom // Empuja todo hacia abajo
        ) {
            Text(
                text = input,
                fontSize = 36.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                text = result,
                fontSize = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.weight(1f)) // Empuja los botones hacia abajo

            // Row principal que contiene los botones de números y operaciones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f) //
            ) {
                // Sección de los botones numéricos (3 columnas y 4 filas)
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .background(Color.DarkGray)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val numberButtons = listOf(
                        listOf("7", "8", "9"),
                        listOf("4", "5", "6"),
                        listOf("1", "2", "3"),
                        listOf(".", "0", "=")
                    )

                    numberButtons.forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            row.forEach { text ->
                                Button(
                                    onClick = {
                                        when (text) {
                                            "=" -> {
                                                result = calculateResult(input)
                                                hasCalculated = true
                                                input = ""
                                            }
                                            else -> {
                                                if(hasCalculated){
                                                    input = text
                                                    hasCalculated = false
                                                }else{
                                                    input += text
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Gray
                                    )
                                ) {
                                    Text(text = text, fontSize = 24.sp, color = Color.White)
                                }
                            }
                        }
                    }
                }

                // Sección de los botones de operaciones (columna derecha)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Gray)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val operations = listOf("DEL", "÷", "×", "-", "+")

                    operations.forEach { op ->
                        Button(
                            onClick = {
                                when (op) {
                                    "DEL" -> {
                                        if (input.isNotEmpty() && !hasCalculated) {
                                            input = input.dropLast(1)
                                        }else if(hasCalculated){
                                            input = ""
                                            hasCalculated = false
                                        }
                                    }
                                    else -> {
                                        if(hasCalculated){
                                            input = result + op
                                            hasCalculated = false
                                        }else{
                                            input += op
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00D6D6)
                            )
                        ) {
                            Text(op, fontSize = 24.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }


    fun calculateResult(mauricio: String): String{
        return try{
            val expr = ExpressionBuilder(mauricio).build()
            val result = expr.evaluate()
            result.toString()
        } catch (e: Exception){
            "error"
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun previewCalculator(){
        Calculator()
    }
}


















