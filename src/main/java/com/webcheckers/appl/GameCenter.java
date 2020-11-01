package com.webcheckers.appl;

import com.webcheckers.model.Game;

import java.util.HashMap;

/**
 * Class to keep track of the Application-wide information and data.
 * This includes one instance of a Player Lobby per Web Checkers session.
 * Eventually, store saved games here. This class additionally keeps track of all
 * Games that are currently running.
 *
 * @author Dmitry Selin
 */
public class GameCenter {

    /**
     * A representation of the domain object, PlayerLobby - a global
     * structure that holds all Players waiting for a Game
     */
    private final PlayerLobby playerLobby;

    /**
     * A global data structure that holds all Games that are currently being played. The String
     * represents the Game ID, while the value object is the Game object
     */
    private final HashMap<String, Game> games;

    /** Creates a new GameCenter object by initializing playerLobby and games objects */
    public GameCenter() {
        playerLobby = new PlayerLobby(this);
        games = new HashMap<>();
    }

    /**
     * Simply adds a newly started Game into the list of all running Games, games.
     *
     * @param game a Game object representing a Game that just began
     */
    public void addGame(Game game) { games.put(game.getId(), game); }

    /**
     * Returns the Game object corresponding to the String entered within the games HashMap.
     *
     * @param id the ID of a Game object within the games HashMap
     *
     * @return the Game object corresponding to the String (Game ID) entered
     */
    public Game getGame(String id) { return games.get(id); }

    /** This method checks every ongoing Game for possible Games where both players have left and removes them. */
    public void removeAbandonedGames() {

        // Iterates through all ongoing Games
        for (String id : games.keySet()) {

            // Removes Games that have been "abandoned"
            if (games.get(id).isGameAbandoned()) {
                System.out.println("GAME REMOVED: " + id);
                games.remove(id);
            }
        }
    }
}
