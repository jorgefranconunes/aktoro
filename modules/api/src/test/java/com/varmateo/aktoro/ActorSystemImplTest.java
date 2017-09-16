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
import com.varmateo.aktoro.Dummy;
import com.varmateo.aktoro.DummyCore;


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

        ActorCoreFactory<Dummy> coreFactory = mock(ActorCoreFactory.class);

        // WHEN
        when(coreFactory.create(any())).thenAnswer(
                invocation -> new DummyCore((ActorRef)invocation.getArguments()[0]));
        Dummy actor = _actorSystem.createActor(coreFactory, Dummy.class);

        // THEN
        ArgumentCaptor<ActorRef> argument =
                ArgumentCaptor.forClass(ActorRef.class);
        verify(coreFactory).create(argument.capture());
        assertThat(argument.getValue().self()).isSameAs(actor);
    }


}
