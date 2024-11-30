package store_tests;

import API.response.json.InventoryDTO;
import API.response.json.OrderDTO;
import core.custom_annotations.NonAuthorized;
import core.enums.ORDER_STATUSES;
import core.enums.TestCache;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.Map;

import static API.implementation.controllers.StoreController.sendDeleteOrderByIDRequest;
import static API.implementation.controllers.StoreController.sendGetOrderByIDRequest;
import static API.implementation.controllers.StoreController.sendGetStoreInventoryRequest;
import static API.implementation.controllers.StoreController.sendPostOrderAtStoreRequest;
import static API.response.mappers.DTOMapper.getInventoryDTOFromResponse;
import static API.response.mappers.DTOMapper.getMapFromDTO;
import static API.response.mappers.DTOMapper.getOrderDTOFromResponse;
import static core.helpers.CacheHelper.getValue;
import static core.helpers.CacheHelper.setValue;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class StoreAPITests extends BaseTest {
    SoftAssertions softAssertion = new SoftAssertions();

    @Test
    public void getStoreInventory() {
        Response response = sendGetStoreInventoryRequest();
        log.info("POST store inventory response is: {}", response.getBody().asString());
        InventoryDTO inventoryDTO = getInventoryDTOFromResponse(response);
        softAssertion.assertThat(HttpStatus.SC_OK)
                .as(String.format("Status code differs from expected, actual status code is %s. Inventory wasn't get",
                        response.getStatusCode())).isEqualTo(response.getStatusCode());
        Map<String, Integer> inventoryDTOMapped = getMapFromDTO(inventoryDTO);
        log.info("Inventory DTO was mapped: {}", inventoryDTOMapped.toString());
        for (String key : inventoryDTOMapped.keySet()) {
            softAssertion.assertThat(inventoryDTOMapped.get(key)).as("Please, check inventory. It returns invalid value").isNotNegative();
        }
        softAssertion.assertAll();
    }

    @Test
    public void createOrderByID() {
        Response response = sendPostOrderAtStoreRequest(ORDER_STATUSES.VALID_ORDER_STATUS);
        int statusCode = response.getStatusCode();
        OrderDTO orderInformation = getOrderDTOFromResponse(response);
        log.info("Response status code: {}", statusCode);
        log.info("POST create order response is: {}", response.getBody().asString());
        softAssertion.assertThat(HttpStatus.SC_OK)
                .as(String.format("Status code differs from expected, actual status code is %s. Order wasn't created",
                        response.getStatusCode())).isEqualTo(response.getStatusCode());
        softAssertion.assertThat(orderInformation.equals(getValue(TestCache.ORDER_DTO))).isTrue().as("Order was " +
                "created with wrong ID, order id differs from id sent to server");
        softAssertion.assertAll();
    }

    @Test
    public void getOrderInfoByID() {
        Response response = sendGetOrderByIDRequest();
        OrderDTO orderInformation = getOrderDTOFromResponse(response);
        log.info("GET order response is: {}", response.getBody().asString());
        softAssertion.assertThat(response.getStatusCode())
                .as(String.format("Status code differs from expected, actual status code is %s. Order wasn't created or information about order " +
                                "can't be retrieved",
                        response.getStatusCode())).isEqualTo(HttpStatus.SC_OK);
        softAssertion.assertThat((orderInformation.getId())).isEqualTo(getValue(TestCache.ORDER_ID));
        softAssertion.assertAll();
    }

    @Test
    public void deleteOrderByID() {
        Response response = sendDeleteOrderByIDRequest();
        log.info("GET order response is: {}", response.getBody().asString());
        assertThat(response.getStatusCode()).as(String.format("Status code differs from expected, " +
                                "actual status code is %s",
                        response.getStatusCode()))
                .isEqualTo(HttpStatus.SC_OK);
    }


    @Test
    public void checkThatUnableToGetAlreadyDeletedOrder() {
        Response response = sendPostOrderAtStoreRequest(ORDER_STATUSES.VALID_ORDER_STATUS);
        log.info("GET order response is: {}", response.getBody().asString());
        assertThat(response.getStatusCode())
                .as(String.format("Status code differs from expected, actual status code is %s. Order wasn't created",
                        response.getStatusCode())).isEqualTo(HttpStatus.SC_OK);
        Integer orderId = response.path("id");
        setValue(TestCache.ORDER_ID, orderId);
        log.info("Current order id was saved at cache: {}", orderId);
        sendDeleteOrderByIDRequest();
        Response getResponseAfterSuccessDelete = sendGetOrderByIDRequest();
        log.info("Try to get non-existing order wasn't successfully: {}", getResponseAfterSuccessDelete.getBody().asString());
        assertThat(getResponseAfterSuccessDelete.getStatusCode())
                .as("It's still able to get information about deleted order").isEqualTo(HttpStatus.SC_NOT_FOUND);
    }


    @Test
    public void createOrderWithNullStatus() {
        Response response = sendPostOrderAtStoreRequest(ORDER_STATUSES.NULL_ORDER_STATUS);
        log.info("GET order response is: {}", response.getBody().asString());
        assertThat(response.getStatusCode()).as(String.format("Status code differs from expected, " +
                                "actual status code is %s",
                        response.getStatusCode()))
                .isEqualTo(SC_OK);
    }

    @Test
    public void checkThatUnableToDeleteAlreadyDeletedOrder() {
        Response response = sendPostOrderAtStoreRequest(ORDER_STATUSES.VALID_ORDER_STATUS);
        assertThat(response.getStatusCode())
                .as(String.format("Status code differs from expected, actual status code is %s. Order wasn't created",
                        response.getStatusCode())).isEqualTo(HttpStatus.SC_OK);
        Integer orderId = response.path("id");
        setValue(TestCache.ORDER_ID, orderId);
        log.info("Current order id was saved at cache: {}", orderId);
        sendDeleteOrderByIDRequest();
        Response deleteResponseForSecondTry = sendDeleteOrderByIDRequest();
        log.info("Second try to delete already deleted order wasn't successfully: {}", deleteResponseForSecondTry.getBody().asString());
        assertThat(deleteResponseForSecondTry.getStatusCode())
                .as("The same order was deleted successfully for second time").isEqualTo(HttpStatus.SC_NOT_FOUND);
    }

    //please note that for next negative scenario it's supposed to see 200 even for
    //non-authorized users with deleted API Key
    // due to existing API implementation but
    //for real project it's supposed to get SC_FORBIDDEN(403) for next scenario

    @NonAuthorized
    @Test
    public void checkThatUnableToGetOrderInfoWithoutAuthorization() {
        assertThat(sendGetOrderByIDRequest().getStatusCode()).isEqualTo(SC_OK);
    }
}
