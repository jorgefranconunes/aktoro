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

import com.varmateo.aktoro.ActorRef;
import com.varmateo.aktoro.ActorRefImpl;
import com.varmateo.aktoro.Dummy;
import com.varmateo.aktoro.DummyCore;


/**
 *
 */
public final class ActorRefImplTest {


    private static final int MAX_THREADS = 4;


    private final ExecutorService _executor =
            Executors.newFixedThreadPool(MAX_THREADS);

    private ActorRef<Dummy> _actorRef;
    private DummyCore _actorCore;
    private Dummy _actor;


    /**
     *
     */
    @Before
    public void setUp() {

        ActorRefImpl<Dummy> actorRefImpl =
                new ActorRefImpl<>(Dummy.class, _executor);
        DummyCore actorCore = new DummyCore(actorRefImpl);

        actorRefImpl.setActorCore(actorCore);

        _actorRef = actorRefImpl;
        _actorCore = actorCore;
        _actor = _actorRef.self();
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
    public void whenVoidMethodInvoked_thenCoreMethodIsExecuted()
            throws InterruptedException {

        // WHEN
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        _actor.saveValue(123, () -> semaphore.release());

        // THEN
        boolean isMethodCompleted =
                semaphore.tryAcquire(1_000, TimeUnit.MILLISECONDS);

        assertThat(isMethodCompleted).isTrue();
        assertThat(_actorCore.getValue()).isEqualTo(123);
    }


    /**
     *
     */
    @Test
    public void whenVoidMethodThrowsException_thenNextCallStillGetsExecuted()
            throws InterruptedException {

        // WHEN
        RuntimeException error = new IllegalStateException("just testing");
        _actor.raiseUncheckedExceptionInVoidMethod(error);

        // THEN
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        _actor.saveValue(123, () -> semaphore.release());
        boolean isMethodCompleted =
                semaphore.tryAcquire(1_000, TimeUnit.MILLISECONDS);

        assertThat(isMethodCompleted).isTrue();
        assertThat(_actorCore.getValue()).isEqualTo(123);
    }


    /**
     *
     */
    @Test
    public void whenNonVoidMethodInvoked_thenCoreMethodIsExecuted() {

        // WHEN
        String originalArgument = "Hello, world!";
        String returnValue = _actor.identity(originalArgument);

        // THEN
        assertThat(returnValue).isEqualTo(originalArgument);
    }


    /**
     *
     */
    @Test
    public void whenNonVoidMethodInvokedTwiceDeep_thenReturnsOk() {

        int value1 = _actor.saveValueAndReturnPrevious(
                10,
                () -> _actor.saveValueAndReturnPrevious(100, () -> {}));
        int value2 = _actorCore.getValue();

        assertThat(value1).isEqualTo(0);
        assertThat(value2).isEqualTo(100);
    }


    /**
     *
     */
    @Test
    public void whenNonVoidMethodThrowsUncheckedExceptionInCore_thenActorThrowsSameException() {

        // WHEN
        RuntimeException originalError =
                new IllegalStateException("just testing");
        Throwable actualError = catchThrowable(
                () -> _actor.raiseUncheckedExceptionInNonVoidMethod(
                        originalError));

        // THEN
        assertThat(actualError).isSameAs(originalError);
    }


}
