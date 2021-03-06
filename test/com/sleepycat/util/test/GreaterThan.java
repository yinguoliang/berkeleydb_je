/*-
 * Copyright (C) 2002, 2017, Oracle and/or its affiliates. All rights reserved.
 *
 * This file was distributed by Oracle as part of a version of Oracle Berkeley
 * DB Java Edition made available at:
 *
 * http://www.oracle.com/technetwork/database/database-technologies/berkeleydb/downloads/index.html
 *
 * Please see the LICENSE file included in the top-level directory of the
 * appropriate version of Oracle Berkeley DB Java Edition for a copy of the
 * license and additional information.
 */

package com.sleepycat.util.test;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/** A JUnit matcher that matches a number greater than the specified value. */
public class GreaterThan extends BaseMatcher<Number> {

    private final Number value;

    /**
     * Returns a matcher that checks for a number greater than the specified
     * value.
     *
     * @param value the value to check against
     * @return the matcher
     */
    public static Matcher greaterThan(Number value) {
        return new GreaterThan(value);
    }

    /**
     * Creates a matcher that checks for a number greater than the specified
     * value.
     *
     * @param value the value to check against
     */
    public GreaterThan(Number value) {
        this.value = value;
    }

    @Override
    public boolean matches(Object item) {
        if (!(item instanceof Number)) {
            return false;
        }
        if ((item instanceof Double) || (item instanceof Float)) {
            final double d = ((Number) item).doubleValue();
            return d > value.doubleValue();
        } else {
            final long l = ((Number) item).longValue();
            return l > value.longValue();
        }
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendText(" number greater than ").appendValue(value);
    }
}
