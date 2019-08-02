package io.axoniq.foodordering.query;

import io.axoniq.foodordering.coreapi.FindFoodCartQuery;
import io.axoniq.foodordering.coreapi.FoodCartCreatedEvent;
import io.axoniq.foodordering.coreapi.ProductDeselectedEvent;
import io.axoniq.foodordering.coreapi.ProductSelectedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
class FoodCartProjector {

    private static final Map<String, FoodCartView> DATA_STORE = new HashMap<>();

//    private final FoodCartViewRepository foodCartViewRepository;
//
//    public FoodCartProjector(FoodCartViewRepository foodCartViewRepository) {
//        this.foodCartViewRepository = foodCartViewRepository;
//    }

    @EventHandler
    public void on(FoodCartCreatedEvent event) {
        FoodCartView foodCartView = new FoodCartView(event.getFoodCartId(), Collections.emptyMap());
        DATA_STORE.put(event.getFoodCartId(), foodCartView);
//        foodCartViewRepository.save(foodCartView);
    }

    @EventHandler
    public void on(ProductSelectedEvent event) {
        DATA_STORE.get(event.getFoodCartId()).addProducts(event.getProductId(), event.getQuantity());
//        foodCartViewRepository.findById(event.getFoodCartId()).ifPresent(
//                foodCartView -> foodCartView.addProducts(event.getProductId(), event.getQuantity())
//        );
    }

    @EventHandler
    public void on(ProductDeselectedEvent event) {
        DATA_STORE.get(event.getFoodCartId()).removeProducts(event.getProductId(), event.getQuantity());
//        foodCartViewRepository.findById(event.getFoodCartId()).ifPresent(
//                foodCartView -> foodCartView.removeProducts(event.getProductId(), event.getQuantity())
//        );
    }

    @QueryHandler
    public FoodCartView handle(FindFoodCartQuery query) {
        return DATA_STORE.get(query.getFoodCartId());
//        return foodCartViewRepository.findById(query.getFoodCartId()).orElse(null);
    }
}
