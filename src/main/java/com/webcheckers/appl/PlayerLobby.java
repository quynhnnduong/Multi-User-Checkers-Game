package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds all existing players and add new players
 * @author Quynh Duong
 */
public class PlayerLobby {

    /**
     * Creates a HashMap for players log in
     */
    private Map<String,Player> players;

    // constructors

    /**
     * New PlayerLobby set up
     */
    public PlayerLobby(){
        players = new HashMap<>();
    }

    /**
     * Adds new player to the players map.
     * @param name name of player to add
     * @return boolean
     */
    public boolean addPlayer(String name) {

        Player player = new Player(name);

        // TODO modify later
        if (players.containsValue(player)){
            return false;
        }
        if(!isNameValid(player.getName())){
            return false;
        }
        players.put(player.getName(), player);
        return true;
    }

    /**
     * Gets the number of players logged in.
     * @return size of players
     */
    public int getPlayerSize(){
        return players.size();
    }

    /**
     * Removes player from the players map.
     * @param player player to remove
     */
    public void removePlayer(Player player){
        players.remove(player);
    }

    /**
     * Get all of the players
     * @return set of players
     */
    public Collection<Player> getAllPlayers() {
        return players.values();
    }

    /**
     * Get all of the players name
     * @return set of players name
     */
    public Collection<String> getAllPlayersName(){
        return players.keySet();
    }

    /**
     * Get all of the players
     * @return set of players map
     */
    public Map<String, Player> getPlayersMap() {
        return players;
    }

    /**
     * get a player from the map
     * @param name name of player to find
     * @return player
     */
    public Player getPlayer(String name){
        return players.get(name);
    }

    /**
     * Checks if a string contains at least one alphanumeric character
     * @param name string to check
     * @return boolean
     */
    public boolean isNameValid(String name){
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

}

