package com.example.myapplication.beasfood.models

data class CartItem(
    var foodName: String?=null,
    var foodPrice: String?=null,
    var foodImage: String?=null,
    var foodDescription: String?=null,
    var foodIngredients: String?=null,
    var foodQuantity: Int?=null,
)
