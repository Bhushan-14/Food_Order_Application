package com.example.myapplication.beasfood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myapplication.beasfood.R
import com.example.myapplication.beasfood.databinding.FragmentProfileBinding
import com.example.myapplication.beasfood.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setUserData()

        binding.saveInforationButton.setOnClickListener{
            val name = binding.profileName.text.toString()
            val address = binding.profileAddress.text.toString()
            val email = binding.profileEmail.text.toString()
            val phone = binding.profilePhone.text.toString()

            updateUserData(name,email,address,phone)
        }

        return binding.root
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {
        val userId = auth.currentUser?.uid
        if(userId != null){
            val userReference = database.getReference("users").child(userId)
            val  userData = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(requireContext(),"Profile Update Failed" ,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("users").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null) {
                            binding.profileName.setText(userProfile.name)
                            binding.profileAddress.setText(userProfile.address)
                            binding.profileEmail.setText(userProfile.email)
                            binding.profilePhone.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}
