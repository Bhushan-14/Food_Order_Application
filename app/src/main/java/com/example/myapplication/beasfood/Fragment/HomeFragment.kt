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
import com.example.myapplication.beasfood.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the image slider
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        // Set up the RecyclerView
        val items = listOf("Burger", "Sandwich", "Momo's","Pizza")
        val prices = listOf("Price 1", "Price 2", "Price 3")
        val images = listOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4)

        val popularAdapter = PopularAdapter(items, prices, images)
        binding.popularRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecycleView.adapter = popularAdapter
    }
}
