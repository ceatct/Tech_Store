package com.inspire.techstore.api.data

data class Cart(
    val userId: Int,
    val date: String,
    val products: List<CartProduct>
)