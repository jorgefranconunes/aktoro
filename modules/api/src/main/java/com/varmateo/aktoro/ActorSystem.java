/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro;

import com.varmateo.aktoro.ActorFactory;


/**
 * Contract for a manager of a set of actors.
 */
public interface ActorSystem {


    /**
     * Creates a new actor.
     *
     * <p>A new actor instance is created through the provided
     * <code>{@link ActorFactory}</code>. A proxy for that actor
     * instance will then be returned. The actor proxy is the visible
     * face of the actor for the outside world. The actor proxy,
     * working within the actor system, has the responsability to
     * ensure the actor methods are called in sequence, and never
     * concurrently.</p>
     *
     * @param <T> Type of objects to be created. This will have to be
     * an interface type.
     *
     * @param actorFactory Used for creating an actual actor
     * instance.
     *
     * @param actorType The class object of the interface the actor
     * instance implements. This will also be the type of the returned
     * proxy actor.
     *
     * @return An object implementing the given <code>actorType</code>
     * interface. That object is a proxy for the actor instance
     * created with the <code>ActorFactory</code>.
     */
    <T> T createActor(
            ActorFactory<T> actorFactory,
            Class<T> actorType);

}
