package com.example.myapplication.beasfood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.beasfood.databinding.ActivityDetailsBinding
import com.example.myapplication.beasfood.models.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var foodName: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodPrice: String? = null
    private var foodIngredients: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        foodName = intent.getStringExtra("MenuItemName")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodIngredients = intent.getStringExtra("MenuItemIngredients")
        foodImage = intent.getStringExtra("MenuItemImage")

        with(binding) {
            detailedFoodName.text = foodName
            detailedFoodDescription.text = foodDescription
            detailedFoodIngredients.text = foodIngredients
            Glide.with(this@DetailsActivity).load(foodImage).into(detailedFoodimage)
        }

        binding.imageButton.setOnClickListener {
            finish()
        }
        binding.detailedAddToCartButton.setOnClickListener {
            checkAndAddItemToCart()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    private fun checkAndAddItemToCart() {
        val userId = auth.currentUser?.uid ?: ""
        val cartReference = database.child("users").child(userId).child("CartItems")

        cartReference.orderByChild("foodName").equalTo(foodName).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(this@DetailsActivity, "Item already added to cart", Toast.LENGTH_SHORT).show()
                } else {
                    val cartItem = CartItem(
                        foodName.toString(),
                        foodPrice.toString(),
                        foodDescription.toString(),
                        foodImage.toString(),
                        foodIngredients.toString()
                    )
                    cartReference.push().setValue(cartItem).addOnSuccessListener {
                        Toast.makeText(this@DetailsActivity, "Item added to cart", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@DetailsActivity, "Failed to add item to cart", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailsActivity, "Error checking cart", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
