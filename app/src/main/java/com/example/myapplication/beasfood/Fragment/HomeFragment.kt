package com.example.myapplication.beasfood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.myapplication.beasfood.R
import com.example.myapplication.beasfood.Adapter.PopularAdapter
import com.example.myapplication.beasfood.MenuBottomSheetFragment
import com.example.myapplication.beasfood.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = listOf(
            SlideModel(R.drawable.banner1, ScaleTypes.FIT),
            SlideModel(R.drawable.banner2, ScaleTypes.FIT),
            SlideModel(R.drawable.banner3, ScaleTypes.FIT)
        )
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        val items = listOf("Burger", "Sandwich", "Momo", "Pizza", "Pasta", "Frankie")
        val prices = listOf("35 Rs.", "40 Rs.", "75 Rs.", "150 Rs.", "60 Rs.", "80 Rs.")
        val images = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6
        )
        val popularAdapter = PopularAdapter(items, prices, images)
        binding.popularRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecycleView.adapter = popularAdapter

        binding.viewMenu.setOnClickListener {
            val bottomSheetFragment = MenuBottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, "MenuBottomSheet")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
