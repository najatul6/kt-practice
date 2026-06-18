package com.example.android30

class ProductRepository {

    private val products = mutableListOf<Product>()

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun getProducts(): List<Product> {
        return products
    }
}