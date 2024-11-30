package core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ORDER_STATUSES {
    VALID_ORDER_STATUS("placed"),
    NULL_ORDER_STATUS(null);

    private final String orderStatus;
}
