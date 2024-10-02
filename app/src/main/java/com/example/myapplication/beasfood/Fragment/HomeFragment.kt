package com.example.myapplication.beasfood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.myapplication.beasfood.Adapter.MenuAdapter
import com.example.myapplication.beasfood.R
import com.example.myapplication.beasfood.MenuBottomSheetFragment
import com.example.myapplication.beasfood.databinding.FragmentHomeBinding
import com.example.myapplication.beasfood.models.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewMenu.setOnClickListener {
            val bottomSheetFragment = MenuBottomSheetFragment()
            bottomSheetFragment.show(parentFragmentManager, "MenuBottomSheet")
        }

        setupImageSlider()
        retrieveAndDisplayPopularItems()

        return binding.root
    }

    private fun setupImageSlider() {
        val imageList = listOf(
            SlideModel(R.drawable.banner1, ScaleTypes.FIT),
            SlideModel(R.drawable.banner2, ScaleTypes.FIT),
            SlideModel(R.drawable.banner3, ScaleTypes.FIT)
        )
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun retrieveAndDisplayPopularItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("Menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val foodItem = foodSnapshot.getValue(MenuItem::class.java)
                    foodItem?.let {
                        menuItems.add(it)
                    }
                }
                displayRandomPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun displayRandomPopularItems() {
        val numItemToShow = 6
        val subsetMenuItems = menuItems.shuffled().take(numItemToShow)
        setPopularItemsAdapter(subsetMenuItems)
    }

    private fun setPopularItemsAdapter(subsetMenuItems: List<MenuItem>) {
        val adapter = MenuAdapter(subsetMenuItems, requireContext())
        binding.popularRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecycleView.adapter = adapter
    }
}
