package com.example.myapplication.beasfood.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.beasfood.Adapter.CartAdapter
import com.example.myapplication.beasfood.PayOutActivity
import com.example.myapplication.beasfood.databinding.FragmentCartBinding
import com.example.myapplication.beasfood.models.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodName: MutableList<String>
    private lateinit var foodPrice: MutableList<String>
    private lateinit var foodImageUri: MutableList<String>
    private lateinit var foodDescription: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var foodQuantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        retrivecartItems()

        binding.proceedButton.setOnClickListener {
            getOrderItemDetails()
        }

        return binding.root
    }

    private fun getOrderItemDetails() {
        val orderIdReference: DatabaseReference =
            database.reference.child("users").child(userID).child("CartItems")
        val foodDescription = mutableListOf<String>()
        val foodImageUri = mutableListOf<String>()
        val foodIngredients = mutableListOf<String>()
        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodQuantities = cartAdapter.getUpdatedItemQuantities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val orderItems = foodSnapshot.getValue(CartItem::class.java)

                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodImageUri.add(it) }
                    orderItems?.foodIngredients?.let { foodIngredients.add(it) }
                }
                orderNow(
                    foodName,
                    foodPrice,
                    foodDescription,
                    foodImageUri,
                    foodQuantities,
                    foodIngredients,
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Order Making Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImageUri: MutableList<String>,
        foodQuantities: MutableList<Int>,
        foodIngredients: MutableList<String>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("foodItemName", foodName as ArrayList<String>)
            intent.putExtra("foodItemPrice", foodPrice as ArrayList<String>)
            intent.putExtra("foodItemDescription", foodDescription as ArrayList<String>)
            intent.putExtra("foodItemImage", foodImageUri as ArrayList<String>)
            intent.putExtra("foodItemQuantity", foodQuantities as ArrayList<Int>)
            intent.putExtra("foodItemIngredients", foodIngredients as ArrayList<String>)
            startActivity(intent)
        }
    }

    private fun retrivecartItems() {
        database = FirebaseDatabase.getInstance()
        userID = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("users").child(userID).child("CartItems")

        foodName = mutableListOf()
        foodPrice = mutableListOf()
        foodImageUri = mutableListOf()
        foodDescription = mutableListOf()
        foodIngredients = mutableListOf()
        foodQuantity = mutableListOf()

        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val cartItem = foodSnapshot.getValue(CartItem::class.java)
                    cartItem?.let {
                        foodName.add(it.foodName ?: "")
                        foodPrice.add(it.foodPrice ?: "")
                        foodImageUri.add(it.foodImage ?: "")
                        foodDescription.add(it.foodDescription ?: "")
                        foodIngredients.add(it.foodIngredients ?: "")
                        foodQuantity.add(it.foodQuantity ?: 1)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setAdapter() {
        cartAdapter = CartAdapter(
            requireContext(),
            foodName,
            foodPrice,
            foodDescription,
            foodImageUri,
            foodQuantity,
            foodIngredients
        )
        binding.cartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartRecyclerView.adapter = cartAdapter
    }

}
