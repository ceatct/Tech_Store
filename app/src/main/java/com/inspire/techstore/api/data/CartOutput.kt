package com.inspire.techstore.api.data

data class CartOutput(
    val id: Int,
    val userId: Int,
    val date: String,
    val products: List<CartProduct>
)