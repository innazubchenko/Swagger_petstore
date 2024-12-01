package API.config;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class RetryOptions {
    public static Response executeWithRetry(Supplier<Response> request, int maxRetries) {
        int attempt = 0;
        Response response;
        do {
            attempt++;
            response = request.get();
            if (response.getStatusCode() != 404) {
                break;
            }
            log.info("Attempt to create order number {} failed with 404 status code. Trying again", attempt);
        } while (attempt < maxRetries);
        if (response.getStatusCode() == 404) {
            log.error("All retry attempts to create order failed with 404, it's unable to create order");
        }
        return response;
    }
}
