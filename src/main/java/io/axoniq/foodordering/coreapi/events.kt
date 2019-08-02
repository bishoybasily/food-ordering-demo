package io.axoniq.foodordering.coreapi

class FoodCartCreatedEvent(
        val foodCartId: String
)

data class ProductSelectedEvent(
        val foodCartId: String,
        val productId: String,
        val quantity: Int
)

data class ProductDeselectedEvent(
        val foodCartId: String,
        val productId: String,
        val quantity: Int
)

data class OrderConfirmedEvent(
        val foodCartId: String
)
