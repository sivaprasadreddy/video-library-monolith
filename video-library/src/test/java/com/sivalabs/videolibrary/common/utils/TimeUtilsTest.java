package com.sivalabs.videolibrary.common.utils;

import static com.sivalabs.videolibrary.common.utils.TimeUtils.*;
import static com.sivalabs.videolibrary.common.utils.TimeUtils.millisToLongDHMS;

import org.junit.jupiter.api.Test;

class TimeUtilsTest {

    @Test
    void testMillisToLongDHMS() {
        System.out.println(((5 * ONE_SECOND) + 123) % 1000);
        System.out.println(millisToLongDHMS(123));
        System.out.println(millisToLongDHMS((5 * ONE_SECOND) + 123));
        System.out.println(millisToLongDHMS(ONE_DAY + ONE_HOUR));
        System.out.println(millisToLongDHMS(ONE_DAY + 2 * ONE_SECOND));
        System.out.println(millisToLongDHMS(ONE_DAY + ONE_HOUR + (2 * ONE_MINUTE)));
        System.out.println(
                millisToLongDHMS((4 * ONE_DAY) + (3 * ONE_HOUR) + (2 * ONE_MINUTE) + ONE_SECOND));
        System.out.println(
                millisToLongDHMS(
                        (5 * ONE_DAY) + (4 * ONE_HOUR) + ONE_MINUTE + (23 * ONE_SECOND) + 123));
        System.out.println(millisToLongDHMS(42 * ONE_DAY));
    }
}
