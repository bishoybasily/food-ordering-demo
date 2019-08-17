package io.axoniq.foodordering.query


data class FoodCartView(
        val foodCartId: String, val products: MutableMap<String, Int> = mutableMapOf()
) {

    /**
     * Adds the given [productId] with the given [amount] to the map of [products].
     * Does this by performing [MutableMap.compute], where the second parameter is a bi-function.
     * The [productId] is not important for the bi-function, hence it's replaced by `_`.
     * Lastly, the `quantity` field in the bi-function is nullable, thus explaining why the elvis operator is in place.
     */
    fun addProducts(productId: String, amount: Int) =
            products.compute(productId) { _, quantity -> (quantity ?: 0) + amount }

    /**
     * Removes the specified [amount] of the given [productId] from the [products] map.
     * Does this by performing [MutableMap.compute], where the second parameter is a bi-function.
     * The [productId] is not important for the bi-function, hence it's replaced by `_`.
     * Lastly, the `quantity` field in the bi-function is nullable, thus explaining why the elvis operator is in place.
     *
     * If the left over quantity is zero, the product will be completely removed from the map.
     */
    fun removeProducts(productId: String, amount: Int) {
        val leftOverQuantity = products.compute(productId) { _, quantity -> (quantity ?: 0) - amount }
        if (leftOverQuantity == 0) {
            products.remove(productId)
        }
    }
}


