package com.example.myapplication.beasfood.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.beasfood.Adapter.MenuAdapter
import com.example.myapplication.beasfood.databinding.FragmentSearchBinding
import com.example.myapplication.beasfood.models.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance()
        menuItems = mutableListOf()
        retrieveMenuItems()
    }

    private fun retrieveMenuItems() {
        val foodRef: DatabaseReference = database.getReference("Menu")

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItems.clear()
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SearchFragment", "Failed to retrieve menu items", error.toException())
            }
        })
    }

    private fun setAdapter() {
        if (menuItems.isEmpty()) {
            Log.d("SearchFragment", "No menu items to display")
        } else {
            menuAdapter = MenuAdapter(menuItems, requireContext())
            binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.menuRecyclerView.adapter = menuAdapter
            Log.d("SearchFragment", "Adapter set with ${menuItems.size} items")
        }
    }
}
