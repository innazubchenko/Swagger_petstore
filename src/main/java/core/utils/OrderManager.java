package core.utils;

import core.enums.ORDER_STATUSES;
import core.enums.TestCache;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static API.controllers.StoreController.sendPostOrderAtStoreRequest;

@Slf4j
public class OrderManager {
    public static int getExistingOrderID() {
        log.info("Trying to get existing Order ID. If there is no existing order, new order will be created");
        int orderID = Optional.ofNullable(CacheManager.getValue(TestCache.ORDER_ID))
                .map(value -> (Integer) value)
                .orElseGet(() -> sendPostOrderAtStoreRequest(ORDER_STATUSES.VALID_ORDER_STATUS)
                        .getBody().path("id"));
        CacheManager.setValue(TestCache.ORDER_ID, orderID);
        log.info("Order id is: {}", orderID);
        return orderID;
    }
}
