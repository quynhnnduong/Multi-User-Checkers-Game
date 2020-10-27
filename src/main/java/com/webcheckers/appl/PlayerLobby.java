package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.*;

/**
 * A domain model object that contains a structure that holds all Players waiting to join a Game of
 * WebCheckers. This class is responsible for maintaining a session-based PlayerLobby for each Player.
 *
 * @author Quynh Duong, Sasha Persaud, Dmitry Selin
 */
public class PlayerLobby {

    /**
     * A Hashmap that holds all Players currently waiting to join a Game. The String key is the name of
     * the Player, while the Player value is the Player object corresponding to the String key.
     */
    private final HashMap<String, Player> players = new HashMap<>();

    /** A site-wide GameCenter that holds the statistics and game for the app */
    private final GameCenter gameCenter;

    /** A String literal used for displaying the number of Players within the PlayerLobby */
    public static final String PLAYER_LOBBY_MSG = " player(s) in lobby";

    /** A String literal used for when there are no Players currently within the PlayerLobby */
    public static final String NO_PLAYERS_MSG = "There are no other players available to play at this time.";

    /**
     * Creates a new PlayerLobby by simply initializing the GameCenter object.
     *
     * @param gameCenter the app-wide GameCenter used for tracking site-wide activities
     */
    public PlayerLobby(GameCenter gameCenter){ this.gameCenter = gameCenter; }

    /**
     * Adds a new Player to players. This method returns true if the Player was added successfully
     * and returns false if the name of the Player already exists within players (every name must be unique).
     *
     * @return true if the Player was added successfully, false if the name of the Player already exists
     */
    public boolean addPlayer(Player player) {

        if (!players.containsKey(player.getName())) {

            // Adds the Player to players
            players.put(player.getName(), player);
            return true;
        }

        return false;
    }

    /**
     * Simply returns the Player object corresponding to the entered name of the Player
     * within the players HashMap.
     *
     * @param name the name of the Player
     * @return the Player object corresponding to the entered name (found in the players HashMap)
     */
    public Player getPlayer (String name) { return players.get(name); }

    /**
     * Returns the number of Players that are not currently playing a Game.
     *
     * @return number of Player in PlayerLobby
     */
    public int getNumOfPlayers() { return players.size(); }

    /**
     * Removes a particular Player from the PlayerLobby.
     *
     * @param player Player to be removed
     */
    public void removePlayer(Player player) { players.remove(player.getName()); }

    /**
     * Returns an ArrayList of all the Player objects within the players HashMap (the values list).
     *
     * @return an ArrayList of all the Players in PlayerLobby
     */
    public ArrayList<Player> getPlayers() { return new ArrayList<>(players.values()); }

    /**
     * Matches two Players together as opponents. This method is executed when a Player
     * has selected another Player from the PlayerLobby to join a WebCheckers Game with.
     *
     * @param player1 a Player that called player2 to start a WebCheckers Game with
     * @param player2 the Player being called to start a Game with player1
     */
    public void setOpponentMatch(Player  player1, Player player2) {
        player1.setOpponent(player2);
        player2.setOpponent(player1);
    }

    /**
     * The message that is to be displayed every time a Player enters the PlayerLobby. If there is more than just
     * the current Player in the PlayerLobby, then the number of Players will be displayed in the message. However,
     * if the PlayerLobby only contains the current Player, then a message saying that there are "no available
     * players" will be displayed.
     *
     * @return a String message regarding the current state of the PlayerLobby
     */
    public String getLobbyMessage() {
        int numOfPlayers = getNumOfPlayers();

        // The displayed number of Players will not include the current Player
        if (numOfPlayers > 1)
            return (--numOfPlayers + PLAYER_LOBBY_MSG);

        return NO_PLAYERS_MSG;
    }
}

