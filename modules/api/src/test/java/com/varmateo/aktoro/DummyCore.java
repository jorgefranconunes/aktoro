/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro;

import com.varmateo.aktoro.ActorRef;
import com.varmateo.aktoro.Dummy;


/**
 * An actor core used in unit tests.
 */
public final class DummyCore
        implements Dummy {


    private ActorRef<Dummy> _actorRef;
    private int _value = 0;


    /**
     *
     */
    public DummyCore(final ActorRef<Dummy> actorRef) {

        _actorRef = actorRef;
    }


    /**
     *
     */
    @Override
    public void saveValue(
            final int value,
            final Runnable completionCallback) {

        _value = value;
        completionCallback.run();
    }


    /**
     *
     */
    @Override
    public int saveValueAndReturnPrevious(
            final int value,
            final Runnable completionCallback) {

        int previousValue = _value;

        _value = value;
        completionCallback.run();

        return previousValue;
    }


    /**
     *
     */
    public int getValue() {

        return _value;
    }


    /**
     *
     */
    @Override
    public String identity(final String something) {

        return something;
    }


    /**
     *
     */
    @Override
    public String raiseUncheckedExceptionInNonVoidMethod(
            final RuntimeException error) {

        throw error;
    }


    /**
     *
     */
    @Override
    public void raiseUncheckedExceptionInVoidMethod(
            final RuntimeException error) {

        throw error;
    }


}
