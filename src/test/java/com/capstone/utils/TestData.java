// ABOUTME: Centralized test data constants to avoid hardcoding in test classes.
// ABOUTME: All expected values, error messages, and form data are defined here.
package com.capstone.utils;

public final class TestData {

    private TestData() {
    }

    public static final String VALID_USER = ConfigReader.get("username");
    public static final String VALID_PASSWORD = ConfigReader.get("password");
    public static final String LOCKED_USER = ConfigReader.get("lockedUser");
    public static final String INVALID_PASSWORD = "wrong_password";

    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "User";
    public static final String ZIP_CODE = "12345";

    public static final int EXPECTED_PRODUCT_COUNT = 6;
    public static final String PRODUCTS_TITLE = "Products";
    public static final String ORDER_CONFIRMATION = "Thank you for your order!";

    public static final String ERROR_USERNAME_REQUIRED = "Username is required";
    public static final String ERROR_PASSWORD_REQUIRED = "Password is required";
    public static final String ERROR_LOCKED_OUT = "Sorry, this user has been locked out";
    public static final String ERROR_CREDENTIALS_MISMATCH = "Username and password do not match";
    public static final String ERROR_FIRSTNAME_REQUIRED = "First Name is required";
    public static final String ERROR_LASTNAME_REQUIRED = "Last Name is required";
    public static final String ERROR_ZIPCODE_REQUIRED = "Postal Code is required";
}
