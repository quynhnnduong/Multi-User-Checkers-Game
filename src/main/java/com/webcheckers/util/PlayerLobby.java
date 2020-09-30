package com.webcheckers.util;

import com.webcheckers.model.Player;

import java.util.ArrayList;

public class PlayerLobby {

    /**
     * Create an ArrayList for players log in
     */
    private ArrayList<Player> playerList;

    public PlayerLobby(){
        playerList = new ArrayList<Player>();
    }

    /**
     * Signs a player in.
     *
     * @param name name of player
     * @return player object
     */
    public Player newPlayer(String name) {
        Player player = new Player(name);
        playerList.add(player);
        return player;
    }

    /**
     * Gets the number of players logged in.
     * @return size of playerList
     */
    public int getPlayerCount(){
        return playerList.size();
    }

    /**
     * Adds player to the playerList.
     * @param player player to add
     */
    public void addPlayer(Player player) {
        playerList.add(player);
    }


    /**
     * Removes player from the playerList.
     * @param player
     */
    public void removePlayer(Player player){
        playerList.remove(player);
    }

//    public Player getPlayer(String name){
//
//    }

}

