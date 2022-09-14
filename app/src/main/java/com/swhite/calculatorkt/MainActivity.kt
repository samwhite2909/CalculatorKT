package com.swhite.calculatorkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    //Setting up text UI component.
    private var textView : TextView? = null

    //Creating values for calculator logic.
    var lastNumeric : Boolean = false
    var lastDecimal : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Linking UI component text to the UI.
        textView = findViewById(R.id.textView)
    }

    fun onDigit(view: View) {
        //Access the button that was pressed from the view and append the digit to the text.
        textView?.append((view as Button).text)
        lastNumeric = true
        lastDecimal = false
    }

    //Clears out the contents of the calculator.
    fun onClear(view: View) {
        textView?.text = ""
    }

    //Adds a decimal point if applicable.
    fun onDecimal(view: View) {
        if(lastNumeric && !lastDecimal) {
            textView?.append(".")
            lastNumeric = false
            lastDecimal = true
        }
    }

    //Handles operator button press.
    fun onOperator(view: View) {
        textView?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                textView?.append((view as Button).text)
                lastNumeric = false
                lastDecimal = false
            }
        }
    }

    //Performs calculation and displays the result on equals button press.
    fun onEqual(view: View) {
        if(lastNumeric) {
            var textViewValue = textView?.text.toString()
            var prefix = ""
            try{
                //Handles the event that the first number is negative.
                if (textViewValue.startsWith("-")) {
                    prefix = "-"
                    textViewValue = textViewValue.substring(1)
                }
                //Subtracts the second number from the first with appropriate handling for
                //negative numbers.
                if(textViewValue.contains("-")) {
                    val splitValue = textViewValue.split("-")
                    var numOne = splitValue[0]
                    val numTwo = splitValue[1]

                    if(prefix.isNotEmpty()){
                        numOne = prefix + numOne
                    }

                    textView?.text = removeZeroAfterDecimal((numOne.toDouble() - numTwo.toDouble()).toString())

                //Adds the two numbers together.
                } else if(textViewValue.contains("+")) {

                    val splitValue = textViewValue.split("+")
                    var numOne = splitValue[0]
                    val numTwo = splitValue[1]

                    if(prefix.isNotEmpty()){
                        numOne = prefix + numOne
                    }

                    textView?.text = removeZeroAfterDecimal((numOne.toDouble() + numTwo.toDouble()).toString())

                //Multiplies the two numbers together.
                } else if (textViewValue.contains("*")) {

                    val splitValue = textViewValue.split("*")
                    var numOne = splitValue[0]
                    val numTwo = splitValue[1]

                    if(prefix.isNotEmpty()){
                        numOne = prefix + numOne
                    }

                    textView?.text = removeZeroAfterDecimal((numOne.toDouble() * numTwo.toDouble()).toString())

                //Divides the first number by the second.
                } else if (textViewValue.contains("/")) {

                    val splitValue = textViewValue.split("/")
                    var numOne = splitValue[0]
                    val numTwo = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        numOne = prefix + numOne
                    }

                    textView?.text = removeZeroAfterDecimal((numOne.toDouble() / numTwo.toDouble()).toString())
                }

            //Logs any arithmetic errors to the console.
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    //Removes decimal value from integers for displaying the result.
    private fun removeZeroAfterDecimal(result: String) : String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    //Checks whether we have an operator inside of the sum on the calculator already.
    //Allows for negative numbers.
    private fun isOperatorAdded(value: String) : Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")
        }
    }

}