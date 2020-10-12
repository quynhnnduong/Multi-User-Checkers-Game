package com.webcheckers.appl;

import java.util.logging.Logger;

/**Class to keep track of the Application-wide information and data.
 * This includes one instance of a Player Lobby per Web Checkers session.
 * Eventually, store saved games here. */
public class GameCenter {
    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

    //The guessing game has player centers access the game center, but never the game center access the player lobby
    public final String PLAYERS_ONLINE = ("%d Players are online. \n Join them!");
    public final String NO_PLAYERS = ("No one is playing Web Checkers right now :(");

    /** Make a new PlayerLobby for the newly connected player
     *
     * @return PlayerLobby object
     */
    public PlayerLobby newPlayerLobby(){
        return new PlayerLobby(this);
    }

    //PlayerLobby Object
    private PlayerLobby playerLobby = newPlayerLobby();

    int PlayerLobbySize = 0;

    public void addPlayerToLobby(){
        this.PlayerLobbySize++;
    }

    /**
     * Get a user message about the players online
     *
     * @return
     *   The message to the visitor about the number of players online.
     */
    public String getPlayersMessage() {
        if(PlayerLobbySize >= 1) {
            return String.format(PLAYERS_ONLINE, playerLobby.getPlayerSize());
        } else {
            return NO_PLAYERS;
        }
    }
}
