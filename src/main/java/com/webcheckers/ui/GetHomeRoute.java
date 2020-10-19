package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;
import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page. This class contains the handle() method that is run when the '/' or
 * '/home' route is requested. This route displays the home page of the application while dealing with certain edge
 * cases.
 *
 * @author Bryan Basham, Dmitry Selin
 */
public class GetHomeRoute implements Route {

  /**A Logger that outputs messages relating the performance of the application to the terminal */
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  /** The passed in global GameCenter object (tracks site-wide game statistics) */
  private final GameCenter gameCenter;

  /** The WebCheckers PlayerLobby (holds all logged-in Player objects) */
  private final PlayerLobby playerLobby;

  /** The TemplateEngine that renders all HTML .ftl files */
  private final TemplateEngine templateEngine;

  /**A Message that is displayed at the top of the home screen */
  public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

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
   * Render the WebCheckers home page.
   *
   * @param request  the HTTP request
   * @param response the HTTP response
   *
   * @return the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session httpSession = request.session();

    LOG.finer("GetHomeRoute is invoked.");

    Map<String, Object> vm = new HashMap<>();

    //this makes the current player's state be in game according to the httpSession
    //for showing the error message of choosing to play against someone who's already in game
    httpSession.attribute(MID_GAME_ATTR, false);

    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    //checks if last person current player picked was mid game
    boolean legitOpponent;

    // if this is a brand new browser session (a new challenger approaches)
    if (httpSession.attribute(PLAYERLOBBY_ATTR) == null || httpSession.attribute(PLAYER_ATTR) == null) {

      // get the object that will provide client-specific services for this player
      //say that this player lobby object belongs to the current session - this is the key connecting the current session to the
      //one playerlobby that all the sessions will share
      httpSession.attribute(PLAYERLOBBY_ATTR, playerLobby);

      // show the not signed in page
      vm.put(LOGGED_IN_ATTR, false);

      legitOpponent = true;
    }
    else {
      //tells the playerlobby to get the player with the name of this session's player
      Player currentPlayer = httpSession.attribute(PLAYER_ATTR);
      vm.put("currentUser", currentPlayer);

      //check if the last person they fought against was valid to choose whether or not to display the message
      legitOpponent = httpSession.attribute(LEGIT_OPPONENT_ATTR);

      //gets the players hashset to display all the players
      vm.put("playerList", playerLobby.getPlayers());
      vm.put(LOGGED_IN_ATTR, true);
      vm.put(PLAYER_MSG_ATTR, playerLobby.getLobbyMessage());

      //check if the current player has been called to a game
      if (currentPlayer.isMidGame() || currentPlayer.isCalledForGame()) {

        //append it onto the game url so its a bootleg query param
        response.redirect(WebServer.GAME_URL + "?opponent=" + currentPlayer.getOpponent().getName());
        return halt();
      }
  }

    vm.put(LEGIT_OPPONENT_ATTR, legitOpponent);

    //Anything below here is to reset when the page is reloaded

    //this is a key that will toggle telling someone if the last name they put in getSignIn is valid
    httpSession.attribute(LEGIT_NAME_ATTR, true);

    //this is a key that will toggle telling someone if the last opponent they selected is valid
    httpSession.attribute(LEGIT_OPPONENT_ATTR, true);

      // render the View
      return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }
}
