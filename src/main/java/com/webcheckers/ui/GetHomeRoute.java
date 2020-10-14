package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  //tells whether to show the no log in, or log in screen
  private static final String LOGGED_IN_ATTR = "loggedIn";

  //the object for displaying the player name (not sure why its not being used)
  public static final String PLAYER_ATTR = "player";

  //the object to display the current online players on the home page.
  public static final String PLAYER_MSG_ATTR = "playersMessage";



  //stolen from webcheckers - keeps records of all the games (probably don't need this since player lobby is a combination
  //of gamecenter and player services, in order to remove this, you have to go all the way from application where its made, and
  //remove it later, but since we don't have much time and the website doesn't crash don't worry about it)
  private final GameCenter gameCenter;

  private final TemplateEngine templateEngine;

  //Key in the session attribute map for the player who started the session stating that this player has interracted with the player lobby
  public static final String PLAYERLOBBY_KEY = "playerLobby";

  //the key to determine if a player has entered a name that someone else has
  public static final String LEGIT_NAME = "legitName";

  //the key to determine if a player picked someone previously who wasn't already playing a game (or they didn't pick anyone)
  public static final String LEGIT_OPPONENT = "legitOpponent";

  //the key to determine if a player is currently playing a game
  public static final String MID_GAME_KEY = "midGame";

  //this is the replacement for game center, and there will be one playerlobby created in Application that will be passed into
  //all the routes
  private PlayerLobby playerLobby;
  public static final String PLAYER_LOBBY_MSG = " player(s) in lobby";
  public static final String NO_PLAYERS_MSG = "There are no other players available to play at this time.";

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, final GameCenter gameCenter, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   * If I commented out a piece of code, It was one of my implementations I'm not sure will be useful later -Joel
   * @param request  the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Home page
   * @author Joel Clyne
   */
  @Override
  public Object handle(Request request, Response response) {
    // retrieve the HTTP session
    // this is a magic device that uses a sort of hashmap to give things to the current session
    // ie, the key (playerName) and value (the literal player object)
    // add something with httpSession.attribute(key, value)
    // retrieve something with httpSession.attribute(key)
    final Session httpSession = request.session();

    LOG.finer("GetHomeRoute is invoked.");

    Map<String, Object> vm = new HashMap<>();

    //this makes the current player's state be in game according to the httpSession
    //for showing the error message of choosing to play against someone who's already in game
    httpSession.attribute(MID_GAME_KEY, false);

    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);
    //final PlayerLobby playerLobby = null;
    // access the currently logged in user

    //checks if last person current player picked was mid game
    boolean legitOpponent;

    // if this is a brand new browser session (a new challenger approaches)
    if (httpSession.attribute(PLAYERLOBBY_KEY) == null || httpSession.attribute(PLAYER_ATTR) == null) {

      // get the object that will provide client-specific services for this player
      //say that this player lobby object belongs to the current session - this is the key connecting the current session to the
      //one playerlobby that all the sessions will share
      httpSession.attribute(PLAYERLOBBY_KEY, playerLobby);

      // show the not signed in page
      vm.put(LOGGED_IN_ATTR, false);

      legitOpponent = true;
    }
    else {
      //home.ftl has all the magic which displays the stuff, it gets the name from PostSignInRoute

      //tells the playerlobby to get the player with the name of this session's player
      Player currentPlayer = httpSession.attribute(PLAYER_ATTR);
      vm.put("currentUser", currentPlayer);

      //check if the last person they fought against was valid to choose whether or not to display the message
      legitOpponent = httpSession.attribute(LEGIT_OPPONENT);

      //gets the players hashset to display all the players
      vm.put("playerList", playerLobby.getPlayers());
      vm.put(LOGGED_IN_ATTR, true);
      vm.put(PLAYER_MSG_ATTR, playerLobby.getLobbyMessage());

      //check if the current player has been called to a game
      if (currentPlayer.isCalledForGame()) {

        //get the name of the current player's opponent from player lobby
        Player opponent = currentPlayer.getOpponent();
        String opponentName = opponent.getName();

        //append it onto the game url so its a bootleg query param
        response.redirect(WebServer.GAME_URL + "?opponent=" + opponentName);
      }
  }

    vm.put(LEGIT_OPPONENT, legitOpponent);

    //Anything below here is to reset when the page is reloaded

    //this is a key that will toggle telling someone if the last name they put in getSignIn is valid
    httpSession.attribute(LEGIT_NAME, true);

    //this is a key that will toggle telling someone if the last opponent they selected is valid
    httpSession.attribute(LEGIT_OPPONENT, true);

      // render the View
      return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }
}
