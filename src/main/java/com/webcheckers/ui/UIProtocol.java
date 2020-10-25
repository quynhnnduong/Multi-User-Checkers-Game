package com.webcheckers.ui;

/**
 * A centralized protocol for accessing Route attributes. This class is a pure fabrication
 * 
 *
 * @author Dmitry Selin
 */
public class UIProtocol {

    //tells whether to show the no log in, or log in screen
    public static final String LOGGED_IN_ATTR = "loggedIn";

    //the object for displaying the player (not sure why its not being used)
    public static final String PLAYER_ATTR = "player";

    //the object to display the current online players on the home page.
    public static final String PLAYER_MSG_ATTR = "playersMessage";

    //Key in the session attribute map for the player who started the session stating that this player has interacted with the player lobby
    public static final String PLAYERLOBBY_ATTR = "playerLobby";

    //the key to determine if a player has entered a name that someone else has
    public static final String LEGIT_NAME_ATTR = "legitName";

    //the key to determine if a player picked someone previously who wasn't already playing a game (or they didn't pick anyone)
    public static final String LEGIT_OPPONENT_ATTR = "legitOpponent";

    public static final String GAME_ID_ATTR = "gameID";
}
