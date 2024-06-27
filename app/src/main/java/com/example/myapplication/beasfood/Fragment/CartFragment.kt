package com.example.myapplication.beasfood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.beasfood.Adapter.CartAdapter
import com.example.myapplication.beasfood.R
import com.example.myapplication.beasfood.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val cartFoodName = mutableListOf("Burger", "Sandwich", "Momo", "Pizza", "Pasta", "Frankie")
        val cartFoodPrice = mutableListOf("35 Rs.", "40 Rs.", "75 Rs.", "150 Rs.", "60 Rs.", "80 Rs.")
        val cartFoodImage = mutableListOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6
        )

        val adapter = CartAdapter(cartFoodName, cartFoodPrice, cartFoodImage)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter

        return binding.root
    }
}
