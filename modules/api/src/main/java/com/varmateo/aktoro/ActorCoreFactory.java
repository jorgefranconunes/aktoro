/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro;

import com.varmateo.aktoro.ActorRef;


/**
 * Contract for a factory of actor cores.
 *
 * <p>An <code>ActorFactory</code> is required for creating an actor
 * instance in the actor system through <code>{@link
 * ActorSystem#createActor(ActorCoreFactory,Class)}</code>.
 *
 * @param <T> The type of actor core objects created by this
 * factory.
 */
@FunctionalInterface
public interface ActorCoreFactory<T> {


    /**
     * Creates a new actor core instance.
     *
     * <p>The factory method will receive the <code>{@link
     * ActorRef}</code> with which the new actor instance will be
     * associated to.</p>
     *
     * @param actorSelf The actor reference that the returned actor
     * core instance will be associated to.
     *
     * @return A new actor core instance.
     */
    T create(ActorRef<T> actorSelf);

}
