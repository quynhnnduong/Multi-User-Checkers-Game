package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetHomeRoute;

import java.util.*;

/**
 * Holds all existing players and add new players
 *
 * @author Quynh Duong, Sasha Persaud
 */
public class PlayerLobby {

    /**
     * Creates a HashMap for players log in
     */
    private final HashMap<String, Player> players = new HashMap<>();

    private final GameCenter gameCenter;

    public static final String PLAYER_LOBBY_MSG = " player(s) in lobby";

    public static final String NO_PLAYERS_MSG = "There are no other players available to play at this time.";

    public PlayerLobby(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    /**
     * Adds new player to the players map.
     *
     * @return boolean
     */
    public boolean addPlayer(Player player) {
        if (!players.containsKey(player.getName())) {
            players.put(player.getName(), player);
            return true;
        }

        return false;
    }

    public Player getPlayer (String name) { return players.get(name); }

    /**
     * Gets the number of players logged in.
     *
     * @return size of players
     */
    public int getNumOfPlayers() { return players.size(); }

    /**
     * Removes player from the players map.
     *
     * @param player player to remove
     */
    public void removePlayer(Player player) { players.remove(player.getName()); }

    public ArrayList<Player> getPlayers() { return new ArrayList<>(players.values()); }

    public void setOpponentMatch(Player  player1, Player player2) {
        player1.setOpponent(player2);
        player2.setOpponent(player1);
    }

    public String getLobbyMessage() {
        int numOfPlayers = getNumOfPlayers();

        if (numOfPlayers > 1)
            return (--numOfPlayers + PLAYER_LOBBY_MSG);

        return NO_PLAYERS_MSG;
    }

    /**
     * Prints all the players in the player lobby.
     * Purely for debugging purposes, we can remove it later!
     *
     * @return String
     */
    public String printPlayers() {
        StringBuilder playerString = new StringBuilder();

        for (String name : players.keySet()) {
            playerString.append(name);
            playerString.append(" ");
        }
        return playerString.toString();
    }
}

