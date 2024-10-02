package com.example.myapplication.beasfood.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.beasfood.DetailsActivity
import com.example.myapplication.beasfood.databinding.PopularItemBinding

class PopularAdapter(
    private val items: List<String>,
    private val price: List<String>,
    private val image: List<Int>,
    private val requireContext: Context
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularAdapter.PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularAdapter.PopularViewHolder, position: Int) {
        val item = items[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item, price, images)

        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("MenuItemImage", images)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return minOf(items.size, price.size, image.size)
    }

    class PopularViewHolder(private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView = binding.imageView5

        fun bind(item: String, price: String, images: Int) {
            binding.foodItemPopular.text = item
            binding.pricePopular.text = price
            imageView.setImageResource(images)
        }
    }
}
