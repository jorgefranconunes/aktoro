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

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    public void whenVoidMethodInvoked_thenItIsExecuted()
            throws Exception {

        DummyActor actor = new DummyActor();
        Dummy dummy = _actorSystem.createActor(
                actorRef -> actor,
                Dummy.class);
        Semaphore semaphore = new Semaphore(1);

        // WHEN
        semaphore.acquire();
        dummy.saveValue(
                123,
                () -> semaphore.release());

        // THEN
        boolean isMethodCompleted =
                semaphore.tryAcquire(1_000, TimeUnit.MILLISECONDS);

        assertThat(isMethodCompleted).isTrue();
        assertThat(actor.getValue()).isEqualTo(123);
    }


    /**
     *
     */
    @Test
    public void whenNonVoidMethodInvoked_thenItIsExecutedSynchronously() {

        DummyActor actor = new DummyActor();
        Dummy dummy = _actorSystem.createActor(
                actorRef -> actor,
                Dummy.class);

        // WHEN
        String originalArgument = "Hello, world!";
        String returnValue = dummy.identity(originalArgument);

        // THEN
        assertThat(returnValue).isEqualTo(originalArgument);
    }


    /**
     *
     */
    @Test
    public void whenNonVoidMethodThrowsUncheckedExceptionInCore_thenActorThrowsSameException() {

        DummyActor actor = new DummyActor();
        Dummy dummy = _actorSystem.createActor(
                actorRef -> actor,
                Dummy.class);

        // WHEN
        RuntimeException originalError =
                new IllegalStateException("just testing");
        Throwable actualError = catchThrowable(
                () -> dummy.raiseUncheckedExceptionInNonVoidMethod(originalError));

        // THEN
        assertThat(actualError).isSameAs(originalError);
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
        public Throwable raiseUncheckedExceptionInNonVoidMethod(final RuntimeException error) {

            throw error;
        }


    }


}
