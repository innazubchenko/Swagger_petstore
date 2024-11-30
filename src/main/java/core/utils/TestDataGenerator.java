package core.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    public static int getRandomOrderID() {
        return ThreadLocalRandom.current().nextInt(1, 11);
    }

    public static ZonedDateTime getShipmentDate() {
        return ZonedDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS);
    }
}
