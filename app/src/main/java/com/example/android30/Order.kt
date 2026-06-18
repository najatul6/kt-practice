package com.example.android30

data class Order(
    val orderId: Int,
    val userId: Int,
    val products: List<Product>
)