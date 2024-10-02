package com.example.myapplication.beasfood.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.beasfood.databinding.CartItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartAdapter(
    private val context: Context,
    private val cartItems: MutableList<String>,
    private val cartItemPrices: MutableList<String>,
    private val cartImages: MutableList<String>,
    private val cartDescription: MutableList<String>,
    private val cartQuantity: MutableList<Int>,
    private val cartIngredient: MutableList<String>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val auth = FirebaseAuth.getInstance()

    init {
        val database = FirebaseDatabase.getInstance()
        val userID = auth.currentUser?.uid ?: ""
        cartItemReference = database.reference.child("users").child(userID).child("CartItems")
        itemQuantity = cartQuantity.toIntArray() // Initialize itemQuantity with cartQuantity values
    }

    companion object {
        private var itemQuantity: IntArray = intArrayOf()
        private lateinit var cartItemReference: DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size

    fun getUpdatedItemQuantities(): MutableList<Int> {
        return itemQuantity.toMutableList()
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                cartFoodName.text = cartItems[position]
                cartItemPrice.text = cartItemPrices[position]
                quantity.text = itemQuantity[position].toString()
                val uri = Uri.parse(cartImages[position])
                Glide.with(context).load(uri).into(cartImage)

                plusButton.setOnClickListener {
                    increaseItemQuantity(position)
                }
                minusButton.setOnClickListener {
                    decreaseItemQuantity(position)
                }
                deleteButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }

        private fun decreaseItemQuantity(position: Int) {
            if (itemQuantity[position] > 1) {
                itemQuantity[position]--
                cartQuantity[position] = itemQuantity[position]
                binding.quantity.text = itemQuantity[position].toString()
            }
        }

        private fun increaseItemQuantity(position: Int) {
            itemQuantity[position]++
            cartQuantity[position] = itemQuantity[position]
            binding.quantity.text = itemQuantity[position].toString()
        }

        private fun deleteItem(position: Int) {
            getUniqueKeyAtPosition(position) { uniqueKey ->
                if (uniqueKey != null) {
                    removeItem(position, uniqueKey)
                }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            cartItemReference.child(uniqueKey).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cartItems.removeAt(position)
                    cartItemPrices.removeAt(position)
                    cartImages.removeAt(position)
                    cartDescription.removeAt(position)
                    cartIngredient.removeAt(position)
                    cartQuantity.removeAt(position)
                    itemQuantity =
                        itemQuantity.filterIndexed { index, _ -> index != position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, cartItems.size)
                } else {
                    Toast.makeText(context, "Failed to remove item", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Error removing item", Toast.LENGTH_SHORT).show()
            }
        }

        private fun getUniqueKeyAtPosition(position: Int, onComplete: (String?) -> Unit) {
            cartItemReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey: String? = null
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == position) {
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error retrieving data", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
