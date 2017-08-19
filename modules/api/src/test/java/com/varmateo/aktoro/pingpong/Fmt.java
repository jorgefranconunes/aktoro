/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro.pingpong;


/**
 * Utility functions for formating strings.
 */
final class Fmt {


    /**
     *
     */
    public static String nanosAsMillis(final long nanos) {

        long millis = nanos / 1_000_000;
        long remainder = nanos % 1_000_000;
        long remainderMicros = remainder / 1000;

        return String.format("%d.%03d", millis, remainderMicros);
    }


}
