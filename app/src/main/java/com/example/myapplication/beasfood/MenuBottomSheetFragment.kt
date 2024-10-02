package com.example.myapplication.beasfood

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.beasfood.Adapter.MenuAdapter
import com.example.myapplication.beasfood.databinding.FragmentMenuBottomSheetBinding
import com.example.myapplication.beasfood.models.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)
        binding.buttonBack.setOnClickListener {
            dismiss()
        }
        retrieveMenuItems()
        return binding.root
    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.getReference("Menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItems.clear()
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                Log.d("MenuBottomSheetFragment", "Menu items retrieved: ${menuItems.size}")
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MenuBottomSheetFragment", "Error retrieving menu items", error.toException())
            }
        })
    }

    private fun setAdapter() {
        if (menuItems.isNotEmpty()) {
            val adapter = MenuAdapter(menuItems, requireContext())
            binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.menuRecyclerView.adapter = adapter
            Log.d("MenuBottomSheetFragment", "Adapter set with ${menuItems.size} items")
        } else {
            Log.d("MenuBottomSheetFragment", "No menu items to display")
        }
    }
}
