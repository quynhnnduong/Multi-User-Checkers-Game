package com.webcheckers.ui;

import com.webcheckers.model.BoardView;

/**
 * A centralized protocol for accessing Route attributes. This class is a pure fabrication
 * 
 *
 * @author Dmitry Selin
 */
public class UIProtocol {

    //the object for displaying the player (not sure why its not being used)
    public static final String PLAYER_ATTR = "player";

    //the key to determine if a player has entered a name that someone else has
    public static final String LEGIT_NAME_ATTR = "legitName";

    //the key to determine if a player picked someone previously who wasn't already playing a game (or they didn't pick anyone)
    public static final String LEGIT_OPPONENT_ATTR = "legitOpponent";

    public static final String GAME_ID_ATTR = "gameID";

    public static final String REPLAY_BOARD = "replayBoard";

    public static final String REPLAY_WHITE_VIEW = "replayWhiteView";
}
