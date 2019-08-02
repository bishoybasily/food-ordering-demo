package io.axoniq.foodordering.gui;

import io.axoniq.foodordering.coreapi.CreateFoodCartCommand;
import io.axoniq.foodordering.coreapi.DeselectProductCommand;
import io.axoniq.foodordering.coreapi.FindFoodCartQuery;
import io.axoniq.foodordering.coreapi.SelectProductCommand;
import io.axoniq.foodordering.query.FoodCartView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping("/foodCart")
@RestController
class FoodOrderingController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public FoodOrderingController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<Object> createFoodCart(@RequestParam String id) {
        return commandGateway.send(new CreateFoodCartCommand(id));
    }

    @PostMapping("/{foodCartId}/select/{productId}/quantity/{quantity}")
    public CompletableFuture<Object> selectProduct(@PathVariable("foodCartId") String foodCartId,
                                                   @PathVariable("productId") String productId,
                                                   @PathVariable("quantity") Integer quantity) {
        return commandGateway.send(
                new SelectProductCommand(
                        foodCartId, productId, quantity
                )
        );
    }

    @DeleteMapping("/{foodCartId}/select/{productId}/quantity/{quantity}")
    public CompletableFuture<Object> deselectProduct(@PathVariable("foodCartId") String foodCartId,
                                                     @PathVariable("productId") String productId,
                                                     @PathVariable("quantity") Integer quantity) {
        return commandGateway.send(
                new DeselectProductCommand(
                        foodCartId, productId, quantity
                )
        );
    }

    @GetMapping("/{foodCartId}")
    public CompletableFuture<FoodCartView> findFoodCart(@PathVariable("foodCartId") String foodCartId) {
        return queryGateway.query(
                new FindFoodCartQuery(foodCartId), ResponseTypes.instanceOf(FoodCartView.class)
        );
    }
}
