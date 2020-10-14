package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetHomeRoute;

import java.util.*;

/**
 * Holds all existing players and add new players
 * @author Quynh Duong, Sasha Persaud
 */
public class PlayerLobby {

    /**
     * Creates a HashMap for players log in
     */
    private HashMap<String, Player> players = new HashMap<>();

    /**
     * Holds a like to the one and only GameCenter
     * @author Joel Clyne
     */
    private final GameCenter gameCenter;

    /**
     * New PlayerLobby set up
     */
    public PlayerLobby(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    /**
     * Adds new player to the players map.
     * @return boolean
     */
    public boolean addPlayer(Player player) {

        if (player.isNameValid() && !players.containsKey(player.getName())) {
            players.put(player.getName(), player);
            return true;
        }

        return false;
    }

    /**
     * Look for a Player in the PlayerLobby. If they are found, return the player.
     * If they are not found, return null.
     * @return player, null
     */
    public Player getPlayer (String name) { return players.get(name); }

    /**
     * Gets the number of players logged in.
     * @return size of players
     */
    public int getNumOfPlayers(){ return players.size(); }

    /**
     * Removes player from the players map.
     * @param player player to remove
     */
    public void removePlayer(Player player){ players.remove(player); }

    public ArrayList<Player> getPlayers() { return new ArrayList<>(players.values()); }

    public void setOpponentMatch(Player  player1, Player player2){
        player1.setOpponent(player2);
        player2.setOpponent(player1);
    }

    public Player getPlayerOpponent(Player player){
        return player.getOpponent();
    }

    public int createGameId(Player currentPlayer, Player opponent){
        return gameCenter.generateGameID(currentPlayer, opponent);
    }

    /**
     * Get all of the players
     * @return set of players

    public Collection<Player> getAllPlayers() {
        return players.values();
    }
     */
    public String getLobbyMessage() {
        int numOfPlayers = getNumOfPlayers();

        if (numOfPlayers > 1)
            return (--numOfPlayers + GetHomeRoute.PLAYER_LOBBY_MSG);

        return GetHomeRoute.NO_PLAYERS_MSG;
    }
}

