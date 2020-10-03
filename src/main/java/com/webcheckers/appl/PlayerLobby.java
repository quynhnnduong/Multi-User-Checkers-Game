package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Holds all existing players and add new players
 * @author Quynh Duong, Sasha Persaud
 */
public class PlayerLobby {
    //
    // attributes
    //

    /**
     * Creates a HashMap for players log in
     */
    private Set<Player> players = new HashSet<>();

    /**
     * Holds a like to the one and only GameCenter
     * @author Joel Clyne
     */
    private final GameCenter gameCenter;

    //
    // constructors
    //

    /**
     * New PlayerLobby set up
     */
    public PlayerLobby(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }


    /**
     * Adds new player to the players map.
     * @param name name of player to add
     * @return boolean
     */
    public boolean addPlayer(String name) {
        Player newPlayer = new Player(name);

        // As long as this Player with this exact name is not already in the set of players,
        // and as long as the name this player has is valid, this player can be added to the set.
        // TODO: (Sasha) Information Expert: Should isNameValid be contained in the Player class, since it concerns Player data, not PlayerLobby data?

        if (!players.contains(newPlayer) && isNameValid(name)){
            players.add(newPlayer);
            return true;
        }

        return false;
    }

    /**
     * Look for a Player in the PlayerLobby. If they are found, return the player.
     * If they are not found, return null.
     * @param name name of player to find
     * @return player, null
     */
    public Player getPlayer(String name){
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
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

    public Collection<Player> getAllPlayers() {
        return players.values();
    }
     */

    /**
     * Get all of the players name
     * @return set of players name

    public Collection<String> getAllPlayersName(){
        return players.keySet();
    }
     */

    /**
     * Get all of the players
     * @return set of players map

    public Map<String, Player> getPlayersMap() {
        return players;
    }
     */



    /**
     * Checks if a string contains at least one alphanumeric character or a space
     * @param name string to check
     * @return boolean
     */
    public boolean isNameValid(String name){
        return name.matches("[A-Za-z0-9 ]+");
    }

}

