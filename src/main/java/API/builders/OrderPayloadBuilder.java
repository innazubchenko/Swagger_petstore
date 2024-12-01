package API.builders;

import API.models.OrderDTO;
import core.enums.ORDER_STATUSES;
import core.enums.TestCache;
import lombok.extern.slf4j.Slf4j;

import static core.utils.CacheManager.setValue;
import static core.utils.TestDataFactory.getRandomOrderID;
import static core.utils.TestDataFactory.getShipmentDate;

@Slf4j
public class OrderPayloadBuilder {
    public static OrderDTO getPayloadForCreateOrderRequest(ORDER_STATUSES orderStatus) {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(getRandomOrderID())
                .petId(getRandomOrderID())
                .quantity(1)
                .shipDate(getShipmentDate())
                .status(orderStatus.getOrderStatus())
                .complete(true)
                .build();
        setValue(TestCache.ORDER_DTO, orderDTO);
        log.info("Body for POST order request was created: {}", orderDTO);
        return orderDTO;
    }
}
