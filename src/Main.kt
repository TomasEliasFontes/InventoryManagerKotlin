/**
 * Represents a product stored in the inventory.
 */
data class Product(
    val id: Int,
    val name: String,
    val category: String,
    var quantity: Int,
    val minimumStock: Int
)

/**
 * Creates a new product and adds it to the inventory.
 */
fun addProduct(products: MutableList<Product>) {
    print("Enter product name: ")
    val name = readLine() ?: ""

    print("Enter category: ")
    val category = readLine() ?: ""

    print("Enter quantity: ")
    val quantity = readLine()?.toIntOrNull() ?: 0

    print("Enter minimum stock: ")
    val minimumStock = readLine()?.toIntOrNull() ?: 0

    // Generate the next available product ID.
    val newId = (products.maxOfOrNull { it.id } ?: 0) + 1

    products.add(
        Product(newId, name, category, quantity, minimumStock)
    )

    println("Product added successfully.")
}

/**
 * Displays all products currently stored in the inventory.
 */
fun displayProducts(products: MutableList<Product>) {
    if (products.isEmpty()) {
        println("No products in inventory.")
        return
    }

    for (product in products) {
        println(
            "ID: ${product.id} | " +
                    "${product.name} (${product.category}) - " +
                    "Qty: ${product.quantity} | " +
                    "Min: ${product.minimumStock}"
        )
    }
}

/**
 * Adds stock to an existing product.
 */
fun addStock(products: MutableList<Product>) {
    print("Enter product ID: ")
    val id = readLine()?.toIntOrNull() ?: -1

    val product = products.find { it.id == id }

    if (product == null) {
        println("Product not found.")
        return
    }

    print("Enter quantity to add: ")
    val amount = readLine()?.toIntOrNull() ?: 0

    if (amount <= 0) {
        println("Amount must be greater than 0.")
        return
    }

    product.quantity += amount

    println("Stock updated. New quantity: ${product.quantity}")
}

/**
 * Removes stock from an existing product.
 */
fun removeStock(products: MutableList<Product>) {
    print("Enter product ID: ")
    val id = readLine()?.toIntOrNull() ?: -1

    val product = products.find { it.id == id }

    if (product == null) {
        println("Product not found.")
        return
    }

    print("Enter quantity to remove: ")
    val amount = readLine()?.toIntOrNull() ?: 0

    if (amount <= 0) {
        println("Amount must be greater than 0.")
        return
    }

    // Prevent the inventory quantity from becoming negative.
    if (amount > product.quantity) {
        println("Not enough stock. Current quantity: ${product.quantity}")
        return
    }

    product.quantity -= amount

    println("Stock updated. New quantity: ${product.quantity}")
}

/**
 * Displays products that are below their minimum stock level.
 */
fun displayLowStock(products: MutableList<Product>) {
    val lowStock = products.filter {
        it.quantity < it.minimumStock
    }

    if (lowStock.isEmpty()) {
        println("No products are low on stock.")
        return
    }

    println("Low-stock products:")

    for (product in lowStock) {
        println(
            "ID: ${product.id} | " +
                    "${product.name} - " +
                    "Qty: ${product.quantity} | " +
                    "Min: ${product.minimumStock}"
        )
    }
}

fun main() {
    // Sample products used when the application starts.
    val products = mutableListOf(
        Product(1, "Coffee", "Food", 15, 5),
        Product(2, "Notebook", "Office", 30, 10),
        Product(3, "Tea", "Food", 8, 5)
    )

    var running = true

    while (running) {
        println()
        println("===========================")
        println("     INVENTORY MANAGER")
        println("===========================")
        println("1. Add product")
        println("2. View products")
        println("3. Add stock")
        println("4. Remove stock")
        println("5. View low-stock products")
        println("6. Exit")
        print("Select an option: ")

        val option = readLine()?.toIntOrNull()

        when (option) {
            1 -> addProduct(products)
            2 -> displayProducts(products)
            3 -> addStock(products)
            4 -> removeStock(products)
            5 -> displayLowStock(products)

            6 -> {
                println("Goodbye!")
                running = false
            }

            else -> println("Invalid option")
        }
    }
}