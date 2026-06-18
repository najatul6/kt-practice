package com.example.android30

object DemoData {

    fun sampleUsers(): List<User> {
        return listOf(
            User(1, "Ashik", "ashik@example.com"),
            User(2, "Rahman", "rahman@example.com")
        )
    }

    fun sampleProducts(): List<Product> {
        return listOf(
            Product(1, "Laptop", 850.0),
            Product(2, "Mouse", 20.0),
            Product(3, "Keyboard", 35.0)
        )
    }
}