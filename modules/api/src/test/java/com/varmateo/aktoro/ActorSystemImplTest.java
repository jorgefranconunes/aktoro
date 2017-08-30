/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.varmateo.aktoro.ActorRef;
import com.varmateo.aktoro.ActorSystem;
import com.varmateo.aktoro.ActorSystemImpl;


/**
 *
 */
public final class ActorSystemImplTest {


    private static final int MAX_THREADS = 4;


    private final ExecutorService _executor =
            Executors.newFixedThreadPool(MAX_THREADS);

    private ActorSystem _actorSystem;


    /**
     *
     */
    @Before
    public void setUp() {

        _actorSystem = new ActorSystemImpl(_executor);
    }


    /**
     *
     */
    @After
    public void tearDown() {
        // Nothing to do, yet...
    }


    /**
     *
     */
    @Test
    public void whenCreateActor_thenFactoryIsInvoked() {

        DummyActor actorCore = new DummyActor();
        ActorCoreFactory<Dummy> coreFactory = mock(ActorCoreFactory.class);

        // WHEN
        when(coreFactory.create(any())).thenReturn(actorCore);
        Dummy actor = _actorSystem.createActor(coreFactory, Dummy.class);

        // THEN
        ArgumentCaptor<ActorRef> argument =
                ArgumentCaptor.forClass(ActorRef.class);
        verify(coreFactory).create(argument.capture());
        assertThat(argument.getValue().self()).isSameAs(actor);
    }


    /**
     *
     */
    private interface Dummy {

        void saveValue(
                int value,
                Runnable completionCallback);

        String identity(String something);

        Throwable raiseUncheckedExceptionInNonVoidMethod(RuntimeException errror);

        void raiseUncheckedExceptionInVoidMethod(RuntimeException errror);
    }


    /**
     *
     */
    private static final class DummyActor
            implements Dummy {


        private int _value = 0;


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
        public Throwable raiseUncheckedExceptionInNonVoidMethod(
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


}
