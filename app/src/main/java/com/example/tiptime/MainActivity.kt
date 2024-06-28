package com.example.tiptime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            calculateButtonOnClick()
        }

        if (savedInstanceState != null) {
            val tip = savedInstanceState.getString("tip")
            binding.tipResult.text = tip
        } else {
            binding.tipResult.text = getString( R.string.tip_amount_s,"-")
        }
    }
    @SuppressLint("StringFormatInvalid")
    private fun calculateButtonOnClick(){
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDouble() ?: return
        val selectedId = binding.tipOption.checkedRadioButtonId

        val tipPercentage = when (selectedId ){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = cost * tipPercentage
        val roundUp = binding.roundUpSwitch.isChecked

        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format( tip )
        binding.tipResult.text = getString( R.string.tip_amount_s, formattedTip )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString( "tip", binding.tipResult.text.toString() )
    }
}

