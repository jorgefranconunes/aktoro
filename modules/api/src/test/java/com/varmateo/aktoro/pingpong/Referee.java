/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.aktoro.pingpong;


/**
 *
 */
public interface Referee {


    /**
     *
     */
    void startGame(
            Player player1,
            Player player2);


    /**
     *
     */
    void endGame(int playCount);


    /**
     * To be called only after the endGame(int) method.
     */
    long getGameDurationNanos();


    /**
     * To be called only after the endGame(int) method.
     */
    int getPlayCount();

}
