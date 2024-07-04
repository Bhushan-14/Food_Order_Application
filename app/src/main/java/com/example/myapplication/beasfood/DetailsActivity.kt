package com.example.myapplication.beasfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.beasfood.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menuItemName = intent.getStringExtra("MenuItemName")
        val menuItemImage = intent.getIntExtra("MenuItemImage", -1)
        val shortDescription = intent.getStringExtra("ShortDescription")
        val ingredients = intent.getStringExtra("Ingredients")

        binding.detailedFoodName.text = menuItemName
        if (menuItemImage != -1) {
            binding.detailedFoodimage.setImageResource(menuItemImage)
        }
        binding.shortDescriptionTextView.text = shortDescription
        binding.ingredientsTextView.text = ingredients

        binding.imageButton.setOnClickListener {
            finish()
        }
    }
}
