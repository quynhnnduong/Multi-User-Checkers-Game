package com.webcheckers.model;

/**
 * This class represents the model object, Player. The Player exists in a singular
 * state at any point in time. When the Player is first created, it starts in the NEW state and
 * switches states according to the actions the user takes when navigating WebCheckers.
 *
 * @author Shubhang Mehrotra (sm9943), Joel Clyne (jmc4514),
 * Sasha Persaud (srp4581), Quynh Duong (qnd5128), Dmitry Selin (des3358)
 */
public class Player {

    /** All the possible states a Player can be in */
    private enum PlayerState {

        /** Represents a new Player - every Player starts in this state */
        NEW,

        /**
         * The next possible state after NEW - enters this
         * state when the Player chooses a valid name on the Sign-In Page
         */
        SIGNED_IN,

        /**
         * Enters this state briefly if the Player is picked by a different Player on the PlayerLobby
         * to start a game with. Transitions to MY_TURN or WAITING when the Game Page is shown.
         */
        CALLED,

        /** A Game state that is entered into when it transitions to this Player's turn */
        MY_TURN,

        /** Represents the Player waiting for his/her turn -
         * a second Game state (alternates with MY_TURN)*/
        WAITING,

        /** Represents someone watching a game in
         * spectator mode or replay mode
         */
        SPECTATING
    }

    /** The name of the Player (entered when signing in) */
    private final String name;

    /** The opponent the Player is facing in WebCheckers (starts as null - gets initialized when a game is started) */
    private Player opponent = null;

    /** The current state of the Player */
    private PlayerState state;

    /**
     * Creates a new Player object.
     *
     * @param name a valid Player name
     */
    public Player(String name) {
        this.name = name;
        state = PlayerState.NEW;
    }

    /**
     * Simply returns the name of the Player.
     *
     * @return the Player's name
     */
    public String getName() { return name; }

    /**
     * Returns a boolean depending on whether a Player is or should be in a Game.
     *
     * @return true if a Player was either called for, or is currently in a Game, otherwise false.
     */
    public boolean inGame() {
        return (state == PlayerState.CALLED || state == PlayerState.MY_TURN || state == PlayerState.WAITING);
    }

    /**
     * Returns true if a Player has been called into a Game of WebCheckers by an opponent,
     * false otherwise.
     *
     * @return if the Player is currently in the CALLED state
     */
    public boolean isCalledForGame() { return state == PlayerState.CALLED; }

    /** Changes a Player's state to SIGNED_IN (happens when a Player exits the Sign-In Page). */
    public void signIn() { state = PlayerState.SIGNED_IN; }

    /**
     * Changes the Player's state to MID_GAME when a new Game of
     * WebCheckers is started or the Player transitions from the CALLED state.
     */
    public void joinGame(boolean myTurn) { state = (myTurn ? PlayerState.MY_TURN : PlayerState.WAITING); }

    /** Represents calling a Player to join a Game (happens when a Player is clicked on at the PlayerLobby). */
    public void call() { state = PlayerState.CALLED; }

    /**
     * Initializes the opponent of Player. This occurs when two Players are matched for a Game.
     *
     * @param opponent a separate Player object - an opposing Player
     */
    public void setOpponent(Player opponent) { this.opponent = opponent; }

    /**
     * Simply returns the opponent of Player.
     *
     * @return the Player's opponent
     */
    public Player getOpponent() { return opponent; }

    /**
     * Returns a boolean is a Player has signed in. Every other state implies that the
     * Player has signed in, except for NEW.
     *
     * @return true if the player has signed in, false otherwise
     */
    public boolean isSignedIn() { return state != PlayerState.NEW; }

    /** When a Player exits a Game, their opponent is set back to null, and their state gets set back to SIGNED_IN */
    public void exitGame() {
        state = PlayerState.SIGNED_IN;
        opponent = null;
    }

    /**
     * Returns if it is the Player's turn.
     *
     * @return true if it is the Player's turn, otherwise false
     */
    public boolean isMyTurn() { return state == PlayerState.MY_TURN; }

    /** Sets the Player's state to MY_TURN when a Player starts his/her turn */
    public void startTurn() { state = PlayerState.MY_TURN; }

    /** When the Player's turn comes to an end, their state gets set to WAITING */
    public void endTurn() { state = PlayerState.WAITING; }

    /**
     * Checks if this Player is playing with a certain other Player passed by through the
     * player argument.
     *
     * @param player a Player object
     * @return true if player is the same object as opponent, otherwise false
     */
    public boolean isPlayingWith(Player player)
    {
        // Checks that opponent is not null before comparing the objects
        if (opponent != null)
            return opponent.equals(player);

        return false;
    }

    /**
     * player begins spectating replay or live game
     */
    public void startSpectating(){
        state = PlayerState.SPECTATING;
    }

    /**
     * player stops spectating replay or live game
     * and is back on the home screen so they become signed in
     */

    public void stopSpectating(){
        state = PlayerState.SIGNED_IN;
    }
}
