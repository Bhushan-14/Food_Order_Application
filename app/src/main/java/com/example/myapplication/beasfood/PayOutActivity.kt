package com.example.myapplication.beasfood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.beasfood.databinding.ActivityPayOutBinding

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.placeMyOrderButton.setOnClickListener{
            val bottomSheetDialog = congratsBottmSheet()
            bottomSheetDialog.show(supportFragmentManager, "Congrats")

        }
    }
}