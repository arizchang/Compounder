package com.example.compounder

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import kotlin.math.pow

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var etCurrentPrincipal: EditText
    private lateinit var etAnnualAddition: EditText
    private lateinit var etYearsToGrow: EditText
    private lateinit var etInterestRate: EditText
    private lateinit var etTimesCompounded: EditText
    private lateinit var btCalculate: Button
    private lateinit var tvFutureValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etCurrentPrincipal = findViewById(R.id.etCurrentPrincipal)
        etAnnualAddition = findViewById(R.id.etAnnualAddition)
        etYearsToGrow = findViewById(R.id.etYearsToGrow)
        etInterestRate = findViewById(R.id.etInterestRate)
        etTimesCompounded = findViewById(R.id.etTimesCompounded)
        btCalculate = findViewById(R.id.btCalculate)
        tvFutureValue = findViewById(R.id.tvFutureValue)
        val oldColors = tvFutureValue.textColors

        btCalculate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                Log.i(TAG, "clicked")
                calculate(oldColors)
            }

        })
    }

    private fun calculate(oldColors: ColorStateList) {
        // error handling
        if (etCurrentPrincipal.text.isEmpty() || etAnnualAddition.text.isEmpty() || etYearsToGrow.text.isEmpty() || etInterestRate.text.isEmpty() || etTimesCompounded.text.isEmpty()) {
            tvFutureValue.text = "FIELDS CANNOT BE EMPTY"
            tvFutureValue.setTextColor(Color.RED)
            hideKeyboard()
            return
        }
        val currentPrincipal = etCurrentPrincipal.text.toString().toFloat()
        val annualAddition = etAnnualAddition.text.toString().toFloat()
        val yearsToGrow = etYearsToGrow.text.toString().toFloat()
        val interestRate = etInterestRate.text.toString().toFloat() / 100
        val timesCompounded = etTimesCompounded.text.toString().toFloat()

        val compoundInterestForPrincipal = currentPrincipal * (1 + interestRate / timesCompounded).pow(timesCompounded * yearsToGrow)
        val futureValOfSeries = annualAddition * (((1 + interestRate / timesCompounded).pow(timesCompounded * yearsToGrow) - 1) / (interestRate / timesCompounded))
        val amount = compoundInterestForPrincipal + futureValOfSeries

        val numFormat = NumberFormat.getCurrencyInstance()
        numFormat.maximumFractionDigits = 2
        val convert = numFormat.format(amount)
        tvFutureValue.text = convert
        tvFutureValue.setTextColor(oldColors)

        hideKeyboard()
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}