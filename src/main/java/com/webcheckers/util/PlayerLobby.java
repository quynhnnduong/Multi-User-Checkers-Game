package com.webcheckers.util;

import com.webcheckers.model.Player;

import java.util.ArrayList;

public class PlayerLobby {

    /**
     * Create an ArrayList for players log in
     */
    private ArrayList<Player> playerList = new ArrayList<Player>();

    /**
     * Signs a player in.
     *
     * @param name name of player
     * @return player object
     */
    public Player player(String name) {
        Player p = new Player(name);
        playerList.add(p);
        return p;
    }


    /**
     * Adds player to the playerList.
     * @param player player to add
     */
    public void add(Player player) {
        playerList.add(player);
    }

    /**
     * Gets the number of players logged in.
     * @return size of playerList
     */
    public int getPlayerCount(){
        return playerList.size();
    }

}

