package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import java.util.HashSet;
import java.util.logging.Logger;

/**Class to keep track of the Application-wide information and data.
 * This includes one instance of a Player Lobby per Web Checkers session.
 * Eventually, store saved games here.
 *
 * The time use this bad boy has come - needs to give every game an id
 * */
public class GameCenter {
    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

    //The guessing game has player centers access the game center, but never the game center access the player lobby
    //private PlayerLobby playerLobby;

    public final String PLAYERS_ONLINE = ("%d Players are online. \n Join them!");
    public final String NO_PLAYERS = ("No one is playing Web Checkers right now :(");

    // Stores all the games currently being played
    private HashSet<Game> games = new HashSet<>();

    /**
     * Gets a game by its ID
     * used to access information about games
     * @param id the id to search for
     * @return the game with the correct id
     */
    Game getGameByID(int id){
        for (Game game : games){
            if (game.getId() == id){
                return game;
            }
        }
        return null;
    }

    //private final  PlayerLobby playerLobby = new PlayerLobby(this);

    /** Make a new PlayerLobby for the newly connected player
     * TODO - We should remove how this functions since playerlobby should be the one that knows how many players there are
     * TODO - With how it is now, player lobby has a game center and gamecenter has a playerlobby, only playerlobby should have a game center
     * @return
     */
    public PlayerLobby newPlayerLobby(){
        return new PlayerLobby(this);
    }

    private PlayerLobby playerLobby = newPlayerLobby();

//    /**
//     * Create a new {WebCheckersGame} game.
//     *
//     * @return
//     *   A new {WebCheckersGame}
//     */
//    public GuessGame getGame() {
//        return new GuessGame();       future implementation.
//    }

    /**
     * Get a user message about the players online
     *
     * @return
     *   The message to the visitor about the number of players online.
     */
    public String getPlayersMessage() {
        if(playerLobby.getPlayerSize() >= 1) {
            return String.format(PLAYERS_ONLINE, playerLobby.getPlayerSize());
        } else {
            return NO_PLAYERS;
        }
    }

    /**
     * Creates a hashcode to be used as a game ID based on the player's names
     * @param currentPlayer
     * @param opponent
     * @return
     */
    public int generateGameID(Player currentPlayer, Player opponent){
        String namesTogether = currentPlayer.getName() + opponent.getName();
        return namesTogether.hashCode();
    }


}

/**
 * bad game center stuff goes below
 * may or may not be used again
 */
