package com.example.myapplication.beasfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.beasfood.Adapter.NotificationAdapter
import com.example.myapplication.beasfood.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBottomBinding.inflate(inflater, container, false)
        val notification = listOf("Your order has been Cancelled Successfully","Order has been taken by the driver successfully ","Congrats Your Order has been Placed")
        val notificationImage = listOf(R.drawable.sademoji, R.drawable.truck,R.drawable.congrats)
        val  adapter = NotificationAdapter(ArrayList(notification),
            ArrayList(notificationImage))

        binding.notifiactionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notifiactionRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {
    }
}