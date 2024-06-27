package com.example.myapplication.beasfood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.beasfood.databinding.FragmentCartBinding
import com.example.myapplication.beasfood.databinding.PopularItemBinding

class PopularAdapter(private val items:List<String>,  private val price:List<String>,private val image:List<Int>,): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularAdapter.PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularAdapter.PopularViewHolder, position: Int) {
        val item = items[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item,price,images)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class PopularViewHolder(private val binding: PopularItemBinding): RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.imageView5
        fun bind(item: String,price: String, images: Int) {
            binding.foodItemPopular.text = item
            binding.pricePopular.text = price
            imageView.setImageResource(images)

        }


    }


}