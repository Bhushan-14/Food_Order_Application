package com.example.myapplication.beasfood


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.beasfood.databinding.ActivityPayOutBinding
import com.example.myapplication.beasfood.models.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PayOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOutBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var userId: String

    private lateinit var foodItemName: ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemImage: ArrayList<String>
    private lateinit var foodItemIngredients: ArrayList<String>
    private lateinit var foodItemQuantity: ArrayList<Int>
    private lateinit var totalAmount: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        saveUserData()

        val intent = intent
        foodItemName = intent.getStringArrayListExtra("foodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("foodItemPrice") as ArrayList<String>
        foodItemDescription = intent.getStringArrayListExtra("foodItemDescription") as ArrayList<String>
        foodItemImage = intent.getStringArrayListExtra("foodItemImage") as ArrayList<String>
        foodItemIngredients = intent.getStringArrayListExtra("foodItemIngredients") as ArrayList<String>
        foodItemQuantity = intent.getIntegerArrayListExtra("foodItemQuantity") as ArrayList<Int>

        totalAmount = calculateTotalAmount().toString() + '₹'
        binding.payoutTotalPrice.setText(totalAmount)

        binding.payBackButton.setOnClickListener{
            finish()
        }

        binding.placeMyOrderButton.setOnClickListener {
            name = binding.payoutName.text.toString().trim()
            address = binding.payOutAddress.text.toString().trim()
            phone = binding.payoutPhone.text.toString().trim()

            if(name.isEmpty() || address.isEmpty() || phone.isEmpty()){
                Toast.makeText(this,"PLease Enter all details",Toast.LENGTH_SHORT).show()
            }else{
                placeOrder()
            }

        }
    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid ?: ""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetails").push().key
        val orderDetails = OrderDetails(userId,name,foodItemName,foodItemPrice,foodItemImage,foodItemQuantity,address,phone,time,itemPushKey,false,false)
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager, "Congrats")
            removeItemsFromCart()
            addOrderToHistory(orderDetails)
        }.addOnFailureListener{
            Toast.makeText(this,"Fail to Order",Toast.LENGTH_SHORT).show()
        }


    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
        databaseReference.child("users").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!).setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemsFromCart() {
        val cartItemsReference = databaseReference.child("users").child(userId).child("CartItems")
        cartItemsReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in foodItemPrice.indices) {
            val price = foodItemPrice[i]
            val priceInValue = if (price.last() == '₹') {
                price.dropLast(1).toIntOrNull() ?: 0
            } else {
                price.toIntOrNull() ?: 0
            }
            val quantity = foodItemQuantity.getOrNull(i) ?: 0
            totalAmount += priceInValue * quantity
        }
        return totalAmount
    }

    private fun saveUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userReference = databaseReference.child("users").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java)
                        val address = snapshot.child("address").getValue(String::class.java)
                        val phone = snapshot.child("phone").getValue(String::class.java)
                        binding.apply {
                            payoutName.setText(name)
                            payOutAddress.setText(address)
                            payoutPhone.setText(phone)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}
