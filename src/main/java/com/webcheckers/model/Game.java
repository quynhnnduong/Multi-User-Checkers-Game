package com.webcheckers.model;

import java.util.ArrayList;

/**
 * A representation of the domain object, 'Checkers Game'. This object stores information
 * regarding each turn that a Player takes. The turns are stored in an ArrayList structure. Additionally,
 * this class controls aspects of the Game such as turn switching, move making, and game finishing.
 *
 * @author Dmitry selin
 */
public class Game {

    /** A structure containing a chronological list of turns that a Player took during the game */
    private final ArrayList<Turn> turns;

    /** A gameID for an instance of Game (redPlayer's Name + whitePlayer's Name) */
    private final String id;

    /** The Player whose Pieces color is RED */
    private final Player redPlayer;

    /** The Player whose Pieces color is WHITE */
    private final Player whitePlayer;

    /** The color of the Player whose turn it is currently */
    private ActiveColor activeColor = ActiveColor.RED;

    /** The BoardView for the redPlayer - the RED Pieces are at the bottom of the board */
    private final BoardView redView = new BoardView(Piece.Color.RED);

    /** The BoardView for the whitePlayer - the WHITE Pieces are at the bottom of the board */
    private final BoardView whiteView = new BoardView(Piece.Color.WHITE);

    /** The enumeration of the color (representing a Player) whose turn it is currently */
    public enum ActiveColor{
        RED,
        WHITE
    }

    /**
     * Creates a new instance of Game - used in GameCenter to keep track of all existing Games.
     *
     * @param id the ID of the Game
     * @param redPlayer the controlling Player of the RED checkers Pieces
     * @param whitePlayer the controlling Player of the WHITE checkers Pieces
     */
    public Game(String id, Player redPlayer, Player whitePlayer){
        this.id = id;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;

        turns = new ArrayList<>();
        addTurn();
    }

    /**
     * Simply returns the player who controls the RED checkers Pieces.
     *
     * @return the redPlayer object
     */
    public Player getRedPlayer() { return redPlayer; }

    /**
     * Returns the player who controls the WHITE checkers Pieces.
     *
     * @return the whitePlayer object
     */
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

    /**
     * Returns the color of the Player whose turn it is currently.
     *
     * @return the color of the Player actively taking his/her turn
     */
    public ActiveColor getActiveColor() { return activeColor; }

    /**
     * Returns the BoardView for the RED Player (RED Pieces are at the bottom of the board)
     *
     * @return the BoardView of redPlayer
     */
    public BoardView getRedView() { return redView; }

    /**
     * Returns the BoardView for the WHITE Player (WHITE Pieces are at the bottom of the board)
     *
     * @return the BoardView of whitePlayer
     */
    public BoardView getWhiteView() { return whiteView; }

    /**
     * Ends the turn for the currently-active Player and passes the turn to the opponent. In this case,
     * if no Players resigned, then the activeColor alternates, and each Player's state is updated to
     * reflect activeColor. Lastly, a Turn is added to the turns ArrayList.
     */
    public void endTurn() {

        // Cannot switch turns if a Player has resigned
        if (!hasPlayerResigned()) {
            //Move move = getCurrentTurn().getFirstMove();
            //Do all the moves
            int moves_left = getCurrentTurn().getAllMovesInTurn().size();
            //System.out.println(" how many turns = " + getCurrentTurn().getAllMovesInTurn().size());
            for (Move move : getCurrentTurn().getAllMovesInTurn()){
                moves_left -= 1;
                if (activeColor == ActiveColor.RED) {

                    if (moves_left == 0) {
                        // Alternates activeColor
                        activeColor = ActiveColor.WHITE;

                        // Updates the Player's states
                        redPlayer.endTurn();
                        whitePlayer.startTurn();
                    }

                        redView.makeMove(move);
                        whiteView.makeMove(move.getFlippedMove());

                }
                else {
                    if (moves_left == 0) {
                        activeColor = ActiveColor.RED;
                        redPlayer.startTurn();
                        whitePlayer.endTurn();
                    }
                    redView.makeMove(move.getFlippedMove());
                    whiteView.makeMove(move);

                }

                // A new Turn is added for the next Player's Turn
                addTurn();
            }
        }
    }

    /**
     * Checks if a Player has resigned from this Game. A Player has resigned if either whitePlayer or redPlayer
     * do not have each other as opponents.
     *
     * @return true if either redPlayer or whitePlayer has resigned
     *         from this Game, false if both Players are still playing
     */
    public boolean hasPlayerResigned() {
        return (!redPlayer.isPlayingWith(whitePlayer) || !whitePlayer.isPlayingWith(redPlayer));
    }

    /**
     * Checks if this Game has been abandoned - meaning that neither the redPlayer nor the whitePlayer
     * are opponents of each other. This indicates that this Game can be safely deleted.
     *
     * @return false if either redPlayer or whitePlayer has the other Player
     *         as an opponent, true if both Players do not match each other as opponents
     */
    public boolean isGameAbandoned() {
        return (!redPlayer.isPlayingWith(whitePlayer) && !whitePlayer.isPlayingWith(redPlayer));
    }

    /**
     * Simply returns the Game's ID.
     *
     * @return the ID of the Game
     */
    public String getId(){ return id; }
}
