package com.rnd.transactiondiffchecker.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    public void givenValidValue_whenProcessValue_thenReturnLowerCaseTrimmedValue() {
        var value = " 0384012056029314-DeDuct";

        var actualResult = StringUtils.getTrimmedLowerCaseValue(value);
        var expectedResult = "0384012056029314-deduct";

        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void givenNullValue_whenProcessValue_thenReturnEmptyString() {
        var actualResult = StringUtils.getTrimmedLowerCaseValue(null);
        var expectedResult = "";

        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void givenEmptyValue_whenProcessValue_thenReturnEmptyString() {
        var actualResult = StringUtils.getTrimmedLowerCaseValue("");
        var expectedResult = "";

        Assertions.assertEquals(actualResult, expectedResult);
    }
}
