package com.example.myapplication.beasfood

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.beasfood.databinding.ActivityLoginActicityBinding

class LoginActivity : AppCompatActivity() {
    private val binding:ActivityLoginActicityBinding by lazy {
        ActivityLoginActicityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.textView6.setOnClickListener{
            val intent = Intent(this,SignupActivity::class.java)
        }
        binding.loginbutton.setOnClickListener{
            val intent = Intent(this,SignupActivity::class.java)
        }
    }
}