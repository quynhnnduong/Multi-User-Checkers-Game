package com.webcheckers.appl;

public class GameCenter {
    /**Class to keep track of the Application-wide information and data.
     * This includes one instance of a Player Lobby per Web Checkers session.
     * Eventually, store saved games here. */

    private PlayerLobby playerLobby;

    public GameCenter() {
        this.playerLobby = new PlayerLobby();
    }



}
