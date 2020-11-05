package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Stores a game as a replay with its id and turns
 */
public class Replay {


    private ArrayList<ReplayMove> turnsList;
    private String gameId;
    private int currentTurn;
    private int maxTurns;
    private Game game;
    private Player redPlayer;
    private Player whitePlayer;

    public Replay(String gameId, ArrayList<ReplayMove> turnsList, Game game){
        this.gameId = gameId;
        this.turnsList = turnsList;
        maxTurns = turnsList.size() - 1;
        this.game = game;
        this.redPlayer = game.getRedPlayer();
        this.whitePlayer = game.getWhitePlayer();
        currentTurn = -1;

    }

    /**
     * save each turn
     */
    //public void saveTurn(){
    //    turns.add(game.getCurrentTurn());
    //}

    public String getGameId() {
        return gameId;
    }

    public ArrayList<ReplayMove> getTurnsList() {
        return turnsList;
    }

    public ReplayMove getTurn(int i){
        return turnsList.get(i);
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

    public void executeReplayMove(Game.ActiveColor currentColor, Move move, BoardView board, BoardView fakeWhite){
        if (currentColor == Game.ActiveColor.RED){
            //do the move on the red view
            //redView.makeMove(move);
            //whiteView.makeMove(move.getFlippedMove());
            board.makeMoveReplayVer(move);
            fakeWhite.makeMoveReplayVer(move.getFlippedMove());
        } else if (currentColor == Game.ActiveColor.WHITE){
            //whiteView.makeMove(move);
            //redView.makeMove(move.getFlippedMove());
            board.makeMoveReplayVer(move.getFlippedMove());
            fakeWhite.makeMoveReplayVer(move);
        }
    }

    public Replay makeCopy(){
        return new Replay(gameId, turnsList, game);
    }
}
