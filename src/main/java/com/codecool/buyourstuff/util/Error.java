package com.codecool.buyourstuff.util;

public interface Error {
    String MALFORMED_FILTER_ID = "Malformed category or supplier id";
    String MALFORMED_CART_ID = "Malformed cart id";
    String MALFORMED_STATUS_CODE = "Malformed status code";

    String INVALID_CART_OPERATION = "Invalid cart operation";

    String COULD_NOT_READ_FIELD = "Couldn't read field";
}
