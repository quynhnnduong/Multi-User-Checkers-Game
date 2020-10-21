package com.webcheckers.appl;

import com.webcheckers.model.Game;

import java.util.HashMap;

/**
 * Class to keep track of the Application-wide information and data.
 * This includes one instance of a Player Lobby per Web Checkers session.
 * Eventually, store saved games here.
 */
public class GameCenter {

    private PlayerLobby playerLobby;

    private final HashMap<String, Game> games;

    public GameCenter() {
        playerLobby = new PlayerLobby(this);
        games = new HashMap<>();
    }

    public void addGame(Game game) { games.put(game.getId(), game); }

    public Game getGame(String id) { return games.get(id); }
}
