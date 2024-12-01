package API.mappers;

import API.models.InventoryDTO;
import API.models.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.custom_exceptions.CustomJsonProcessingException;
import core.enums.ORDER_STATUSES;
import io.restassured.response.Response;
import lombok.SneakyThrows;

import java.util.Map;

import static API.builders.OrderPayloadBuilder.getPayloadForCreateOrderRequest;
import static API.mappers.JacksonDateTimeMapper.createDateTimeMappingHelper;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;

public class DTOMapper {

    @SneakyThrows
    public static OrderDTO getOrderDTOFromResponse(Response response) {
        if (response.getStatusCode() == (SC_OK)) {
            return createDateTimeMappingHelper().readValue(response.getBody().asString(), OrderDTO.class);
        } else {
            throw new CustomJsonProcessingException("Failed to process JSON payload, response status code is " + response.getStatusCode());
        }
    }

    @SneakyThrows
    public static String createPayloadForOrderRequest(ORDER_STATUSES orderStatus) {
        try {
            return createDateTimeMappingHelper().writeValueAsString(getPayloadForCreateOrderRequest(orderStatus));
        } catch (JsonProcessingException e) {
            throw new CustomJsonProcessingException("Failed to process JSON payload: " + e.getMessage());
        }
    }

    @SneakyThrows
    public static InventoryDTO getInventoryDTOFromResponse(Response response) {
        if (response.getStatusCode() == (SC_OK)) {
            return createDateTimeMappingHelper().readValue(response.getBody().asString(), InventoryDTO.class);
        } else {
            throw new CustomJsonProcessingException("Failed to process JSON payload, response status code is " + response.getStatusCode());
        }
    }

    public static Map<String, Integer> getMapFromDTO(InventoryDTO inventoryDTO) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(inventoryDTO, Map.class);
    }
}
