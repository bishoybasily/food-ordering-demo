package io.axoniq.foodordering.query;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.axoniq.foodordering.coreapi.FindFoodCartQuery;
import io.axoniq.foodordering.coreapi.FoodCartCreatedEvent;
import io.axoniq.foodordering.coreapi.ProductDeselectedEvent;
import io.axoniq.foodordering.coreapi.ProductSelectedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
class FoodCartProjector {

    private Map<String, FoodCartView> DATA_STORE = new HashMap<>();
    private File file = new File("/home/bishoybasily/Desktop/ordering.json");
    private Gson gson = new Gson();

    public FoodCartProjector() {
        if (file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                DATA_STORE = gson.fromJson(reader, new TypeToken<Map<String, FoodCartView>>() {
                }.getType());
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            storeIt();
        }
    }

    @EventHandler
    public void on(FoodCartCreatedEvent event) {
        FoodCartView foodCartView = new FoodCartView(event.getFoodCartId(), Collections.emptyMap());
        DATA_STORE.put(event.getFoodCartId(), foodCartView);
        storeIt();
    }

    @EventHandler
    public void on(ProductSelectedEvent event) {
        DATA_STORE.get(event.getFoodCartId()).addProducts(event.getProductId(), event.getQuantity());
        storeIt();
    }

    @EventHandler
    public void on(ProductDeselectedEvent event) {
        DATA_STORE.get(event.getFoodCartId()).removeProducts(event.getProductId(), event.getQuantity());
        storeIt();
    }

    @QueryHandler
    public FoodCartView handle(FindFoodCartQuery query) {
        return DATA_STORE.get(query.getFoodCartId());
    }

    private void storeIt() {
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(DATA_STORE, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
