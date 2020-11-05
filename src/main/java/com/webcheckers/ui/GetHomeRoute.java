package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.ReplayLoader;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;

/**
 * The UI Controller to GET the Home page. This class contains the handle() method that is run when the '/' or the
 * '/home' route is requested. This route displays the home page of the application while dealing with certain edge
 * cases.
 *
 * @author Bryan Basham, Dmitry Selin
 */
public class GetHomeRoute implements Route {

  /** A Logger that outputs messages relating the performance of the application to the terminal */
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  /** The passed in global GameCenter object (tracks site-wide game statistics) */
  private final GameCenter gameCenter;

  /** The WebCheckers PlayerLobby (holds all logged-in Player objects) */
  private final PlayerLobby playerLobby;

  /** The TemplateEngine that renders all HTML .ftl files */
  private final TemplateEngine templateEngine;

  private final ReplayLoader replayLoader;

  /** A Message that is displayed at the top of the home screen */
  public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, final GameCenter gameCenter, PlayerLobby playerLobby, final ReplayLoader replayLoader) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
    this.replayLoader = replayLoader;

    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Each time the '/' or '/home' route is invoked, this method is run to handle the request.
   *
   * @param request  the HTTP request
   * @param response the HTTP response
   *
   * @return the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session session = request.session();

    LOG.finer("GetHomeRoute is invoked.");

    Map<String, Object> vm = new HashMap<>();

    vm.put("title", "Welcome!");
    vm.put("message", WELCOME_MSG);

    // A new browser session or a Player signs in after signing out
    if (session.attribute(PLAYER_ATTR) == null) {

      session.attribute(LEGIT_NAME_ATTR, true);
      vm.put("loggedIn", false);
    }
    else {
      Player currentPlayer = session.attribute(PLAYER_ATTR);

      // Check if currentPlayer has been called to a game
      if (currentPlayer.inGame()) {
        response.redirect(WebServer.GAME_URL + "?opponent=" + currentPlayer.getOpponent().getName());
        // return halt();
      }

      if (session.attribute(GAME_ID_ATTR) != null)
        session.attribute(GAME_ID_ATTR, null);

      vm.put("currentUser", currentPlayer);
      vm.put("playerList", playerLobby.getPlayers());
      vm.put("loggedIn", true);
    }

      //show all the available replays
      ArrayList<Replay> replayList = replayLoader.getAllReplays();
      vm.put("replayList", replayList);

    vm.put("playersMessage", playerLobby.getLobbyMessage());
    vm.put(LEGIT_OPPONENT_ATTR, session.attribute(LEGIT_NAME_ATTR));

    // Resets these attributes upon each home page reload
    session.attribute(LEGIT_NAME_ATTR, true);
    session.attribute(LEGIT_OPPONENT_ATTR, true);

    gameCenter.removeAbandonedGames();

    // Render the Home Page
    return templateEngine.render(new ModelAndView(vm, "home.ftl"));
  }
}
