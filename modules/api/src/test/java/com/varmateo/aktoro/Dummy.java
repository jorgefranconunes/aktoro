/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro;



/**
 * An interface for actors used in unit tests.
 */
public interface Dummy {


    /**
     *
     */
    void saveValue(
            int value,
            Runnable completionCallback);


    /**
     *
     */
    int saveValueAndReturnPrevious(
            int value,
            Runnable completionCallback);


    /**
     *
     */
    String identity(String something);


    /**
     *
     */
    String raiseUncheckedExceptionInNonVoidMethod(RuntimeException errror);


    /**
     *
     */
    void raiseUncheckedExceptionInVoidMethod(RuntimeException errror);

}
