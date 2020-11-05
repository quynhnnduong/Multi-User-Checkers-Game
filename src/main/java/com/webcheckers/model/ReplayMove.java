package com.webcheckers.model;

/**
 * Stores moves to be used by replay
 */
public class ReplayMove {
    private Game.ActiveColor playerColor;
    private Move move;

    public ReplayMove(Game.ActiveColor playerColor, Move move){
        this.playerColor = playerColor;
        this.move = move;
    }

    public Game.ActiveColor getPlayerColor() {
        return playerColor;
    }

    public Move getMove() {
        return move;
    }
}
