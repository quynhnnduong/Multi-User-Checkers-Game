package com.webcheckers.ui;

public class UIProtocol {

    //tells whether to show the no log in, or log in screen
    public static final String LOGGED_IN_ATTR = "loggedIn";

    //the object for displaying the player name (not sure why its not being used)
    public static final String PLAYER_ATTR = "player";

    //the object to display the current online players on the home page.
    public static final String PLAYER_MSG_ATTR = "playersMessage";

    /** Attribute to denote the red player in the session */
    public static final String RED_ATTR = "redPlayer";

    /** Attribute to denote the white player in the session*/
    public static final String WHITE_ATTR = "whitePlayer";

    /** Attribute to denote the board in the session*/
    public static final String BOARD_ATTR = "board";

    //Key in the session attribute map for the player who started the session stating that this player has interracted with the player lobby
    public static final String PLAYERLOBBY_KEY = "playerLobby";

    //the key to determine if a player has entered a name that someone else has
    public static final String LEGIT_NAME = "legitName";

    //the key to determine if a player picked someone previously who wasn't already playing a game (or they didn't pick anyone)
    public static final String LEGIT_OPPONENT = "legitOpponent";

    //the key to determine if a player is currently playing a game
    public static final String MID_GAME_KEY = "midGame";
}
