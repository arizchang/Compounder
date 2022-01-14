package com.example.compounder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        btCalculate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                Log.i(TAG, "clicked")
                calculate()
            }

        })
    }

    private fun calculate() {
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
    }
}