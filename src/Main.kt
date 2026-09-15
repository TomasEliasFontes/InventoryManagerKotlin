/**
 * Represents a single product in the inventory.
 * Uses a data class so Kotlin automatically generates toString(), equals(), and copy(),
 * which is useful for a simple data-holding object like this.
 */
data class Product(
    val id: Int,
    val name: String,
    val category: String,
    var quantity: Int,
    val minimumStock: Int
)

/**
 * Prompts the user for product details and adds a new Product to the list.
 * The new ID is generated automatically based on the highest existing ID.
 */
fun addProduct(products: MutableList<Product>) {
    print("Enter product name: ")
    val name = readLine() ?: ""  // Default to empty string if input fails

    print("Enter category: ")
    val category = readLine() ?: ""

    print("Enter quantity: ")
    val quantity = readLine()?.toIntOrNull() ?: 0  // Default to 0 if input isn't a valid number

    print("Enter minimum stock: ")
    val minimumStock = readLine()?.toIntOrNull() ?: 0

    // Find the highest existing ID and add 1; if the list is empty, start at 1
    val newId = (products.maxOfOrNull { it.id } ?: 0) + 1
    products.add(Product(newId, name, category, quantity, minimumStock))
    println("Product added successfully.")
}

/**
 * Prints every product currently in the inventory.
 */
fun displayProducts(products: MutableList<Product>) {
    if (products.isEmpty()) {
        println("No products in inventory.")
        return
    }
    for (product in products) {
        println("ID: ${product.id} | ${product.name} (${product.category}) - Qty: ${product.quantity} | Min: ${product.minimumStock}")
    }
}

/**
 * Increases the quantity of an existing product by a given amount.
 */
fun addStock(products: MutableList<Product>) {
    print("Enter product ID: ")
    val id = readLine()?.toIntOrNull() ?: -1

    // Look up the product by ID; null if not found
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
 * Decreases the quantity of an existing product by a given amount.
 * Prevents removing more stock than is currently available.
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

    // Prevent negative stock
    if (amount > product.quantity) {
        println("Not enough stock. Current quantity: ${product.quantity}")
        return
    }

    product.quantity -= amount
    println("Stock updated. New quantity: ${product.quantity}")
}

/**
 * Displays only the products whose quantity has fallen below their minimum stock level.
 */
fun displayLowStock(products: MutableList<Product>) {
    val lowStock = products.filter { it.quantity < it.minimumStock }

    if (lowStock.isEmpty()) {
        println("No products are low on stock.")
        return
    }

    println("Low-stock products:")
    for (product in lowStock) {
        println("ID: ${product.id} | ${product.name} - Qty: ${product.quantity} | Min: ${product.minimumStock}")
    }
}

fun main() {
    // Starting inventory with a few sample products
    val products = mutableListOf<Product>()
    products.add(Product(1, "Coffee", "Food", 15, 5))
    products.add(Product(2, "Notebook", "Office", 30, 10))
    products.add(Product(3, "Tea", "Food", 8, 5))

    var running = true

    // Keep showing the menu until the user chooses to exit (option 6)
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