package com.webcheckers.model;

import java.util.ArrayList;

/**
 * A representation of the domain object, 'Checkers Game'. This object stores information
 * regarding each turn that a Player takes. The turns are stored in an ArrayList structure.
 *
 * @author Dmitry selin
 */
public class Game {

    /** A structure containing a chronological list of turns that a Player took during the game */
    private final ArrayList<Turn> turns;

    /** A gameID for an instance of Game (redPlayer's Name + whitePlayer's Name) */
    private final String id;

    private final Player redPlayer;

    private final Player whitePlayer;

    private ActiveColor activeColor = ActiveColor.RED;

    private final BoardView redView = new BoardView(Piece.Color.RED);

    private final BoardView whiteView = new BoardView(Piece.Color.WHITE);

    /** The color (representing a Player) whose turn it is currently */
    public enum ActiveColor{
        RED,
        WHITE
    }

    /**
     * Creates a new instance of Game - used in GameCenter to keep track of all existing Games.
     *
     * @param id the ID of the Game
     */
    public Game(String id, Player redPlayer, Player whitePlayer){
        this.id = id;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;

        turns = new ArrayList<>();
        addTurn();
    }

    public Player getRedPlayer() { return redPlayer; }

    public Player getWhitePlayer() { return whitePlayer; }

    /** Adds a new Turn to turns - used when it is the Player's turn */
    private void addTurn() { turns.add(new Turn()); }

    /**
     * Returns the last Turn object in turns. This represents the Turn that the Player
     * is currently taking.
     *
     * @return the Turn that the Player is currently on (still modifying)
     */
    public Turn getCurrentTurn() { return turns.get(turns.size() - 1); }

    public ActiveColor getActiveColor() { return activeColor; }

    public BoardView getRedView() { return redView; }

    public BoardView getWhiteView() { return whiteView; }

    public void endTurn() {
        if (!hasPlayerResigned()) {
            Move move = getCurrentTurn().getFirstMove();

            if (activeColor == ActiveColor.RED) {
                activeColor = ActiveColor.WHITE;

                redPlayer.endTurn();
                whitePlayer.startTurn();

                redView.makeMove(move);
                whiteView.makeMove(move.getFlippedMove());
            }
            else {
                activeColor = ActiveColor.RED;

                redPlayer.startTurn();
                whitePlayer.endTurn();

                redView.makeMove(move.getFlippedMove());
                whiteView.makeMove(move);
            }

            addTurn();
        }
    }

    public boolean hasPlayerResigned() { return !(redPlayer.inGame() && whitePlayer.inGame()); }

    public boolean hasGameEnded() { return (!redPlayer.inGame() && !whitePlayer.inGame()); }

    /**
     * Simply returns the Game's ID.
     *
     * @return the ID of the Game
     */
    public String getId(){ return id; }
}
