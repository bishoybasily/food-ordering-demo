package io.axoniq.foodordering.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateFoodCartCommand(
        @TargetAggregateIdentifier val foodCartId: String
)

data class SelectProductCommand(
        @TargetAggregateIdentifier val foodCartId: String,
        val productId: String,
        val quantity: Int
)

data class DeselectProductCommand(
        @TargetAggregateIdentifier val foodCartId: String,
        val productId: String,
        val quantity: Int
)

data class ConfirmOrderCommand(
        @TargetAggregateIdentifier val foodCartId: String
)
