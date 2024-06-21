package com.example.myapplication.beasfood

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.beasfood.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private val binding:ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.signupbutton.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
        }
        binding.textView11.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
        }
    }
}