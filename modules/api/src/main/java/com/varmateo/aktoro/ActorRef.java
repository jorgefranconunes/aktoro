/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro;


/**
 * A wrapper for an actor instance.
 *
 * <p>Instances of this class are tipically used only inside the class
 * implementing an actor. The <code>ActorRef</code> allows the actor
 * to execute lambdas and callbacks without closing on its internal
 * context.</p>
 *
 * <p>When an actor is created an <code>ActorRef</code> instance is
 * passed to its factory (see
 * <code>{@link ActorSystem#createActor(Class,ActorFactory)}</code>. The
 * actor can then use that <code>ActorRef</code> to execute lambdas,
 * by calling <code>{@link #perform(Runnable)}</code>, or to pass references
 * of itself to outside, by calling <code>{@link #self()}</code>.</p>
 *
 * @param T The type of the actor.
 */
public interface ActorRef<T> {


    /**
     * Performs an action in the scope of the associated actor. The
     * given action will be executed as if it had been called as a
     * method of this actor. This means that when the given action is
     * being executed no other thread will be executing methods of the
     * actor. Also, this method dos not block, and returns without
     * waiting for the action to complete.
     *
     * @param action The action to be executed.
     */
    void perform(Runnable action);


    /**
     * Fetches the actor for this <code>ActorRef</code>.
     *
     * @return The associated actor.
     */
    T self();


}
