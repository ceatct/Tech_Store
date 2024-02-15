package com.inspire.techstore.api.data

data class UserCart (
    val id: Int,
    val userId: Int,
    val date: String,
    val products: List<CartProduct>,
    val __v: Int
)