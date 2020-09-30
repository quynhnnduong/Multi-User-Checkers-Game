package com.webcheckers.appl;

public class GameCenter {
    /**Class to keep track of the Application-wide information and data.
     * This includes one instance of a Player Lobby per Web Checkers session.
     * Eventually, store saved games here. */
    //The guessing game has player centers access the game center, but never the game center access the player lobby
    //private PlayerLobby playerLobby;

    public GameCenter() {

        //this.playerLobby = new PlayerLobby();
    }

    /** Make a new PlayerLobby for the newly connected player
     *
     * @return
     */
    public PlayerLobby newPlayerLobby(){
        return new PlayerLobby(this);
    }



}
