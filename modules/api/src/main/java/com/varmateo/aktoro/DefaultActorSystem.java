/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro;

import java.util.concurrent.Executor;


/**
 * Factory of <code>ActorSystem</code>.
 */
public final class DefaultActorSystem {


    /**
     *
     */
    private DefaultActorSystem() {
        // Nothing to do.
    }


    /**
     * Creates a new <code>ActorSystem</code>.
     *
     * <p>The method calls on the actor cores will be performed as
     * tasks submitted to the given executor.
     *
     * @param executor Used for invking method calls on the actor
     * cores.
     *
     * @return A newly instantiated actor system.
     */
    public static ActorSystem create(final Executor executor) {

        return new ActorSystemImpl(executor);
    }

}
