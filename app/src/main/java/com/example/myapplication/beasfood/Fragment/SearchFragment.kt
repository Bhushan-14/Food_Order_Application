package com.example.myapplication.beasfood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.beasfood.Adapter.MenuAdapter
import com.example.myapplication.beasfood.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuItems = mutableListOf("Burger", "Sandwich", "Momo", "Pizza", "Pasta", "Frankie")
        val menuItemPrice = mutableListOf("35 Rs.", "40 Rs.", "75 Rs.", "150 Rs.", "60 Rs.", "80 Rs.")
        val menuImages = mutableListOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6
        )
        val shortDescriptions = mutableListOf(
            "Short description for Burger",
            "Short description for Sandwich",
            "Short description for Momo",
            "Short description for Pizza",
            "Short description for Pasta",
            "Short description for Frankie"
        )
        val ingredients = mutableListOf(
            "1. Burger bun \n2. Patty \n3. Lettuce \n4. Tomato \n5. Onion",
            "1. Sandwich bread \n2. Cheese \n3. Lettuce \n4. Tomato \n5. Cucumber",
            "1. Momo dough \n2. Minced meat filling \n3. Spices \n4. Sauce",
            "1. Pizza dough \n2. Tomato sauce \n3. Cheese \n4. Toppings of choice",
            "1. Pasta \n2. Tomato sauce \n3. Cheese \n4. Vegetables of choice",
            "1. Frankie wrap \n2. Vegetables \n3. Sauce \n4. Paneer or meat"
        )

        menuAdapter = MenuAdapter(menuItems, menuItemPrice, menuImages, shortDescriptions, ingredients, requireContext())
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter = menuAdapter
    }
}
