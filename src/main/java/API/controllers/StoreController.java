package API.controllers;

import API.config.RetryOptions;
import core.enums.ORDER_STATUSES;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.function.Supplier;

import static API.config.APIConfig.getBaseUri;
import static API.mappers.DTOMapper.createPayloadForOrderRequest;
import static core.utils.OrderManager.getExistingOrderID;

public class StoreController {
    private static final String INVENTORY_ENDPOINT = "/store/inventory";
    private static final String ORDER_ENDPOINT = "store/order";
    private static final String ORDER_ID_PARAM = "orderId";


    public static Response sendGetStoreInventoryRequest() {
        return RestAssured
                .given()
                .baseUri(getBaseUri())
                .basePath(INVENTORY_ENDPOINT)
                .log().all()
                .when()
                .get();
    }

    public static Response sendPostOrderAtStoreRequest(ORDER_STATUSES orderStatus) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(getBaseUri())
                .basePath(ORDER_ENDPOINT)
                .log().all()
                .body(createPayloadForOrderRequest(orderStatus))
                .when()
                .post();
    }

    public static Response sendGetOrderByIDRequest() {
        Supplier<Response> response = () -> RestAssured
                .given()
                .baseUri(getBaseUri())
                .basePath(ORDER_ENDPOINT + "/{" + ORDER_ID_PARAM + "}")
                .pathParam(ORDER_ID_PARAM, getExistingOrderID())
                .log().all()
                .when()
                .get();
        return RetryOptions.executeWithRetry(response, 5);
    }

    public static Response sendDeleteOrderByIDRequest() {
        return RestAssured
                .given()
                .baseUri(getBaseUri())
                .basePath(ORDER_ENDPOINT + "/{" + ORDER_ID_PARAM + "}")
                .pathParam(ORDER_ID_PARAM, getExistingOrderID())
                .log().all()
                .when()
                .delete();
    }
}
