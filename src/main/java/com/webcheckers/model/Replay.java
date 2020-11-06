package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Stores a game as a replay with its id and turns
 * @author Joel Clyne
 */
public class Replay {

    //The list of all the moves taken in the replay
    private final ArrayList<ReplayMove> turnsList;

    //The id of the game the replay represents
    private final String gameId;

    //The currentTurn being shown in the replay
    private int currentTurn;

    //The amount of moves in the replay
    private final int maxTurns;

    //The game the replay shows
    private final Game game;

    //The red player in the replay
    private final Player redPlayer;

    //The white player in the replay
    private final Player whitePlayer;

    public Replay(String gameId, ArrayList<ReplayMove> turnsList, Game game){
        this.gameId = gameId;
        this.turnsList = turnsList;
        maxTurns = turnsList.size() - 1;
        this.game = game;
        this.redPlayer = game.getRedPlayer();
        this.whitePlayer = game.getWhitePlayer();
        currentTurn = -1;

    }

    public String getGameId() {
        return gameId;
    }

    public void incrementTurn(){
        if (currentTurn < turnsList.size() - 1) {
            currentTurn += 1;
        }
    }

    public void decrementTurn(){
        if (currentTurn > 0) {
            currentTurn -= 1;
        }
    }

    public ReplayMove getCurrentTurn(){
        return turnsList.get(currentTurn);
    }

    public Player getRed(){
        return redPlayer;
    }

    public Player getWhite(){
        return whitePlayer;
    }

    public Game getGame(){
        return game;
    }

    public int getCurrentTurnNum(){
        return currentTurn;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    /**
     * Executes a move in the replay
     * @param currentColor the color of the player who did the move
     * @param move the move that was done
     * @param board the spectator's board
     * @param fakeWhite the "white player" board hidden to the spectator
     */
    public void executeReplayMove(Game.ActiveColor currentColor, Move move, BoardView board, BoardView fakeWhite){
        if (currentColor == Game.ActiveColor.RED){
            //do the move on the red view
            board.makeMoveReplayVer(move);
            fakeWhite.makeMoveReplayVer(move.getFlippedMove());
        } else if (currentColor == Game.ActiveColor.WHITE){
            board.makeMoveReplayVer(move.getFlippedMove());
            fakeWhite.makeMoveReplayVer(move);
        }
    }

    /**
     * Makes a copy of the current replay
     * @return a copy of this replay
     */
    public Replay makeCopy(){
        return new Replay(gameId, turnsList, game);
    }
}
