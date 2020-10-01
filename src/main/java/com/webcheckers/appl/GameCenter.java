package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import java.util.logging.Logger;

/**Class to keep track of the Application-wide information and data.
 * This includes one instance of a Player Lobby per Web Checkers session.
 * Eventually, store saved games here. */
public class GameCenter {
    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

    //The guessing game has player centers access the game center, but never the game center access the player lobby
    //private PlayerLobby playerLobby;


    // Output strings
    public final Message NO_PLAYERS_MSG = Message.info("No one is playing Web Checkers right now :(");
    public final Message PLAYERS_ONLINE_MSG = Message.info("Players are online");

    public final String PLAYERS_ONLINE = ("%d Players are online. \n Join them!");
    public final String NO_PLAYERS = ("No one is playing Web Checkers right now :(");

    private PlayerLobby playerLobby = new PlayerLobby(this);

//    /** Make a new PlayerLobby for the newly connected player
//     *
//     * @return
//     */
//    public PlayerLobby newPlayerLobby(){
//        return new PlayerLobby(this);
//    }
//
//    private PlayerLobby playerLobby = newPlayerLobby();

//    /**
//     * Create a new {WebCheckersGame} game.
//     *
//     * @return
//     *   A new {WebCheckersGame}
//     */
//    public GuessGame getGame() {
//        return new GuessGame();       future implementation.
//    }

//    public String getPlayersOnlineMsg(){
//        return String.format("%d Players are currently Online. Join them!", playerLobby.getPlayerSize());
//    }

    /**
     * Get a user message about the players online
     *
     * @return
     *   The message to the visitor about the number of players online.
     */
    public String getPlayersMessage() {
        if(playerLobby.getPlayerSize() >= 1) {
            return String.format(PLAYERS_ONLINE, playerLobby.getPlayerSize());
        } else {
            return NO_PLAYERS;
        }
    }
}
