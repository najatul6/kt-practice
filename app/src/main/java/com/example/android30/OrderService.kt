package com.example.android30

class OrderService {

    fun calculateTotal(order: Order): Double {
        return order.products.sumOf { it.price }
    }
}