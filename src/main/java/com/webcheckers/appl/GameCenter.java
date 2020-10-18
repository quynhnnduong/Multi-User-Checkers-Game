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
}
