/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro;

import java.util.concurrent.Executor;

import com.varmateo.aktoro.ActorCoreFactory;
import com.varmateo.aktoro.ActorRefImpl;
import com.varmateo.aktoro.ActorSystem;


/**
 * Manages a set of actors.
 *
 * <p>The actors managed by one given <code>ActorSystemImpl</code>
 * have their methods executed by one of the threads in the
 * <code>Executor</code> specified at constructor time.</p>
 */
/* default */ final class ActorSystemImpl implements ActorSystem {


    private final Executor _executor;


    /**
     * @param executor Used for executing actor methods.
     */
    /* default */ ActorSystemImpl(final Executor executor) {

        _executor = executor;
    }


    /**
     * {@inheritDoc}
     */
    public <T> T createActor(
            final ActorCoreFactory<T> actorCoreFactory,
            final Class<T> actorType) {

        ActorRefImpl<T> actorRef = new ActorRefImpl<>(actorType, _executor);
        T actorCore = actorCoreFactory.create(actorRef);

        actorRef.setActorCore(actorCore);

        return actorRef.self();
    }

}
