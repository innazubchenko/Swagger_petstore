package core.helpers;

import core.enums.ORDER_STATUSES;
import core.enums.TestCache;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static API.implementation.controllers.StoreController.sendPostOrderAtStoreRequest;
import static core.helpers.CacheHelper.getValue;
import static core.helpers.CacheHelper.setValue;

@Slf4j
public class OrderHelper {
    public static int getExistingOrderID() {
        log.info("Trying to get existing Order ID. If there is no existing order, new order will be created");
        int orderID = Optional.ofNullable(getValue(TestCache.ORDER_ID))
                .map(value -> (Integer) value)
                .orElseGet(() -> sendPostOrderAtStoreRequest(ORDER_STATUSES.VALID_ORDER_STATUS)
                        .getBody().path("id"));
        setValue(TestCache.ORDER_ID, orderID);
        log.info("Order id is: {}", orderID);
        return orderID;
    }
}
