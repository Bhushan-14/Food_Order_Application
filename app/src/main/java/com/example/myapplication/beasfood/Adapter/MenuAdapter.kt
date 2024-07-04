package com.example.myapplication.beasfood.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.beasfood.DetailsActivity
import com.example.myapplication.beasfood.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItems: MutableList<String>,
    private val menuItemPrice: MutableList<String>,
    private val menuImage: MutableList<Int>,
    private val shortDescriptions: MutableList<String>,
    private val ingredients: MutableList<String>,
    private val context: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    init {
        require(menuItems.size == menuItemPrice.size && menuItemPrice.size == menuImage.size &&
                menuItems.size == shortDescriptions.size && shortDescriptions.size == ingredients.size) {
            "All lists must have the same size"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView = binding.menuImage

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(context, DetailsActivity::class.java).apply {
                        putExtra("MenuItemName", menuItems[position])
                        putExtra("MenuItemImage", menuImage[position])
                        putExtra("ShortDescription", shortDescriptions[position])
                        putExtra("Ingredients", ingredients[position])
                    }
                    context.startActivity(intent)
                }
            }
        }

        fun bind(position: Int) {
            binding.menuFoodName.text = menuItems[position]
            binding.menuPrice.text = menuItemPrice[position]
            imageView.setImageResource(menuImage[position])
        }
    }
}
