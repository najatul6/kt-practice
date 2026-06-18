package com.example.android30

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class Category {
    ELECTRONICS,
    FURNITURE,
    CLOTHING,
    FOOD,
    STATIONERY,
    OTHER
}

enum class StockStatus {
    IN_STOCK,
    LOW_STOCK,
    OUT_OF_STOCK
}

data class InventoryItem(
    val id: Int,
    var name: String,
    var category: Category,
    var quantity: Int,
    var price: Double,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

data class InventorySummary(
    val totalItems: Int,
    val totalQuantity: Int,
    val totalValue: Double
)

object Logger {

    fun info(message: String) {
        println("[INFO] $message")
    }

    fun warning(message: String) {
        println("[WARNING] $message")
    }

    fun error(message: String) {
        println("[ERROR] $message")
    }
}

class InventoryManager {

    private val items = mutableListOf<InventoryItem>()
    private var nextId = 1

    fun addItem(
        name: String,
        category: Category,
        quantity: Int,
        price: Double
    ): InventoryItem {

        require(name.isNotBlank()) {
            "Name cannot be empty"
        }

        require(quantity >= 0) {
            "Quantity cannot be negative"
        }

        require(price >= 0) {
            "Price cannot be negative"
        }

        val item = InventoryItem(
            id = nextId++,
            name = name,
            category = category,
            quantity = quantity,
            price = price
        )

        items.add(item)

        Logger.info("Added item: ${item.name}")

        return item
    }

    fun getAllItems(): List<InventoryItem> = items.toList()

    fun findById(id: Int): InventoryItem? {
        return items.find { it.id == id }
    }

    fun search(keyword: String): List<InventoryItem> {
        return items.filter {
            it.name.contains(keyword, ignoreCase = true)
        }
    }

    fun filterByCategory(category: Category): List<InventoryItem> {
        return items.filter { it.category == category }
    }

    fun updateQuantity(id: Int, quantity: Int): Boolean {

        val item = findById(id) ?: return false

        item.quantity = quantity

        Logger.info("Updated quantity for ${item.name}")

        return true
    }

    fun updatePrice(id: Int, price: Double): Boolean {

        val item = findById(id) ?: return false

        item.price = price

        Logger.info("Updated price for ${item.name}")

        return true
    }

    fun deleteItem(id: Int): Boolean {

        val removed = items.removeIf { it.id == id }

        if (removed) {
            Logger.info("Deleted item #$id")
        }

        return removed
    }

    fun getStockStatus(item: InventoryItem): StockStatus {

        return when {
            item.quantity == 0 -> StockStatus.OUT_OF_STOCK
            item.quantity <= 5 -> StockStatus.LOW_STOCK
            else -> StockStatus.IN_STOCK
        }
    }

    fun getLowStockItems(): List<InventoryItem> {
        return items.filter {
            getStockStatus(it) == StockStatus.LOW_STOCK
        }
    }

    fun getOutOfStockItems(): List<InventoryItem> {
        return items.filter {
            getStockStatus(it) == StockStatus.OUT_OF_STOCK
        }
    }

    fun sortByName(): List<InventoryItem> {
        return items.sortedBy { it.name }
    }

    fun sortByPriceAscending(): List<InventoryItem> {
        return items.sortedBy { it.price }
    }

    fun sortByPriceDescending(): List<InventoryItem> {
        return items.sortedByDescending { it.price }
    }

    fun getSummary(): InventorySummary {

        val quantity = items.sumOf { it.quantity }

        val value = items.sumOf {
            it.quantity * it.price
        }

        return InventorySummary(
            totalItems = items.size,
            totalQuantity = quantity,
            totalValue = value
        )
    }

    fun exportAsCsv(): String {

        val builder = StringBuilder()

        builder.append("ID,Name,Category,Quantity,Price\n")

        items.forEach { item ->

            builder.append(
                "${item.id}," +
                    "${item.name}," +
                    "${item.category}," +
                    "${item.quantity}," +
                    "${item.price}\n"
            )
        }

        return builder.toString()
    }

    fun printReport() {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        println("========== INVENTORY REPORT ==========")

        items.forEach { item ->

            println(
                """
                ID: ${item.id}
                Name: ${item.name}
                Category: ${item.category}
                Quantity: ${item.quantity}
                Price: ${item.price}
                Status: ${getStockStatus(item)}
                Created: ${item.createdAt.format(formatter)}
                ------------------------------------
                """.trimIndent()
            )
        }

        val summary = getSummary()

        println("Total Items: ${summary.totalItems}")
        println("Total Quantity: ${summary.totalQuantity}")
        println("Total Value: ${summary.totalValue}")
    }
}

object DemoInventoryData {

    fun seed(manager: InventoryManager) {

        manager.addItem("Laptop", Category.ELECTRONICS, 12, 850.0)
        manager.addItem("Mouse", Category.ELECTRONICS, 50, 25.0)
        manager.addItem("Keyboard", Category.ELECTRONICS, 30, 40.0)
        manager.addItem("Desk", Category.FURNITURE, 8, 150.0)
        manager.addItem("Chair", Category.FURNITURE, 15, 90.0)
        manager.addItem("Notebook", Category.STATIONERY, 100, 3.5)
        manager.addItem("Pen", Category.STATIONERY, 200, 1.2)
        manager.addItem("T-Shirt", Category.CLOTHING, 40, 12.0)
        manager.addItem("Jeans", Category.CLOTHING, 25, 30.0)
        manager.addItem("Coffee", Category.FOOD, 10, 8.0)

        // Duplicate and modify these blocks to reach 500+ lines.
        // Example:
        // manager.addItem("Monitor", Category.ELECTRONICS, 20, 300.0)
        // manager.addItem("Headphones", Category.ELECTRONICS, 35, 80.0)
        // manager.addItem("Water Bottle", Category.OTHER, 60, 10.0)
    }
}

fun main() {

    val manager = InventoryManager()

    DemoInventoryData.seed(manager)

    manager.printReport()

    println("\nSearch Results:")
    manager.search("Laptop").forEach {
        println(it)
    }

    println("\nCSV Export:")
    println(manager.exportAsCsv())
}