package com.webcheckers.appl;

import com.webcheckers.model.Player;

/**
 * Class to keep track of the Application-wide information and data.
 * This includes one instance of a Player Lobby per Web Checkers session.
 * Eventually, store saved games here.
 */
public class GameCenter {
    private PlayerLobby playerLobby;

    public GameCenter() { playerLobby = new PlayerLobby(this); }

    /**
     * Creates a hashcode to be used as a game ID based on the player's names
     * @param currentPlayer
     * @param opponent
     * @return
     */
    public int generateGameID(Player currentPlayer, Player opponent){
        String namesTogether = currentPlayer.getName() + opponent.getName();
        return namesTogether.hashCode();
    }
}
