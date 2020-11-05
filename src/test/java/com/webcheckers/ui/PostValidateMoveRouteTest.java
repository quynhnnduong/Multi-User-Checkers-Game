package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.lang.reflect.Type;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@Tag("UI-tier")
public class PostValidateMoveRouteTest {
    // Attributes holding mock objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Game game;
    private Turn turn;
    private Gson gson;

    private Position start, end;
    private int rowDiff, colDiff;
    private BoardView redView, whiteView, curView;
    private Game.ActiveColor curColor;

    // Component under test (CuT)
    private PostValidateMoveRoute CuT;

    //friendlies
    private Move move;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        game = mock(Game.class);
        turn = mock(Turn.class);
        start = mock(Position.class);
        end = mock(Position.class);
        move = mock(Move.class);
        rowDiff = 0;
        colDiff = 0;
        redView = mock(BoardView.class);
        whiteView = mock(BoardView.class);
        curView = mock(BoardView.class);
        curColor = Game.ActiveColor.RED;

        when(request.session()).thenReturn(session);

        CuT = new PostValidateMoveRoute(gameCenter);
        gson = new Gson();

    }

    // @Test
    public void testHandleInvalidForwardSingle() {
        // Set up

        // Invoke
        Object result = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: A jump move can be taken this turn.")), result);
    }

    @Test
    public void testIfTurnHasMoves() {
        //  Set up
        String messageText = "{\"sampleMove\": { \"row\": 1,\"col\": 1}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the start and end positions of the move
        when(start.getRow()).thenReturn(0);
        when(start.getCell()).thenReturn(0);

        when(end.getRow()).thenReturn(1);
        when(end.getCell()).thenReturn(1);

        // mock the turn (line 33)
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);
        when(turn.hasMoves()).thenReturn(true);

        // hardcode the row/col differences - needed for compile not test

        // mock the board views - needed for compile not test
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getRedView()).thenReturn(redView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getWhiteView()).thenReturn(whiteView);

        // hardcode the current color - needed for compile not test
        curColor = Game.ActiveColor.RED;
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getActiveColor()).thenReturn(curColor);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Can only move once")), validMessage);

    }

    @Test
    public void testValidateJumpMoveSuccessful() {
        Piece piece = mock(Piece.class);
        BoardView.JumpType jumpType = BoardView.JumpType.FORWARD_RIGHT;
        when(move.getStart()).thenReturn(start);
        when(start.getRow()).thenReturn(1);
        when(start.getCell()).thenReturn(1);
        when(curView.getPiece(1, 1)).thenReturn(piece);
        when(curView.isJumpPossible(any(Piece.class), anyInt(), anyInt(), any(BoardView.JumpType.class))).thenReturn(true);

        Message result = CuT.validateJumpMove(turn, move, curView, jumpType);

        assertEquals(Message.info("Valid Move"), result);

    }

    @Test
    public void testValidateJumpMoveUnsuccessful() {
        Piece piece = mock(Piece.class);
        BoardView.JumpType jumpType = BoardView.JumpType.FORWARD_RIGHT;
        when(move.getStart()).thenReturn(start);
        when(start.getRow()).thenReturn(1);
        when(start.getCell()).thenReturn(1);
        when(curView.getPiece(1, 1)).thenReturn(piece);
        when(curView.isJumpPossible(any(Piece.class), anyInt(), anyInt(), any(BoardView.JumpType.class))).thenReturn(false);

        Message result = CuT.validateJumpMove(turn, move, curView, jumpType);

        assertEquals(Message.error("INVALID MOVE: Not a valid jump move."), result);

    }

    @Test
    public void testValidateSingleMoveSuccessful() {
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);
        Message result = CuT.validateSingleMove(turn, move, curView, curColor);
        assertEquals(Message.error("INVALID MOVE: A jump move can be taken this turn."), result);
    }

    @Test
    public void testValidateSingleMoveUnsuccessful() {
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(false);
        Message result = CuT.validateSingleMove(turn, move, curView, curColor);
        assertEquals(Message.info("Valid Move"), result);
    }

    @Test
    public void testGetMoveTypeForwardSingle() {
        PostValidateMoveRoute.MoveType result1 = CuT.getMoveType(-1, 1);
        PostValidateMoveRoute.MoveType result2 = CuT.getMoveType(-1, 5);
        PostValidateMoveRoute.MoveType result3 = CuT.getMoveType(5, 1);
        PostValidateMoveRoute.MoveType result4 = CuT.getMoveType(5, 5);

        assertEquals(PostValidateMoveRoute.MoveType.FORWARD_SINGLE, result1);
        assertNotEquals(PostValidateMoveRoute.MoveType.FORWARD_SINGLE, result2);
        assertNotEquals(PostValidateMoveRoute.MoveType.FORWARD_SINGLE, result3);
        assertNotEquals(PostValidateMoveRoute.MoveType.FORWARD_SINGLE, result4);

    }

    @Test
    public void testGetMoveTypeForwardJump() {
        PostValidateMoveRoute.MoveType result1 = CuT.getMoveType(-2, 2);
        PostValidateMoveRoute.MoveType result2 = CuT.getMoveType(-2, 5);
        PostValidateMoveRoute.MoveType result3 = CuT.getMoveType(5, 2);
        PostValidateMoveRoute.MoveType result4 = CuT.getMoveType(5, 5);

        assertEquals(PostValidateMoveRoute.MoveType.FORWARD_JUMP, result1);
        assertNotEquals(PostValidateMoveRoute.MoveType.FORWARD_JUMP, result2);
        assertNotEquals(PostValidateMoveRoute.MoveType.FORWARD_JUMP, result3);
        assertNotEquals(PostValidateMoveRoute.MoveType.FORWARD_JUMP, result4);
    }

    @Test
    public void testGetMoveTypeBackwardSingle() {
        PostValidateMoveRoute.MoveType result1 = CuT.getMoveType(1, 1);
        PostValidateMoveRoute.MoveType result2 = CuT.getMoveType(1, 5);
        PostValidateMoveRoute.MoveType result3 = CuT.getMoveType(5, 1);
        PostValidateMoveRoute.MoveType result4 = CuT.getMoveType(5, 5);

        assertEquals(PostValidateMoveRoute.MoveType.BACKWARD_SINGLE, result1);
        assertNotEquals(PostValidateMoveRoute.MoveType.BACKWARD_SINGLE, result2);
        assertNotEquals(PostValidateMoveRoute.MoveType.BACKWARD_SINGLE, result3);
        assertNotEquals(PostValidateMoveRoute.MoveType.BACKWARD_SINGLE, result4);
    }

    @Test
    public void testGetMoveTypeBackwardJump() {
        PostValidateMoveRoute.MoveType result1 = CuT.getMoveType(2, 2);
        PostValidateMoveRoute.MoveType result2 = CuT.getMoveType(2, 5);
        PostValidateMoveRoute.MoveType result3 = CuT.getMoveType(5, 2);
        PostValidateMoveRoute.MoveType result4 = CuT.getMoveType(5, 5);

        assertEquals(PostValidateMoveRoute.MoveType.BACKWARD_JUMP, result1);
        assertNotEquals(PostValidateMoveRoute.MoveType.BACKWARD_JUMP, result2);
        assertNotEquals(PostValidateMoveRoute.MoveType.BACKWARD_JUMP, result3);
        assertNotEquals(PostValidateMoveRoute.MoveType.BACKWARD_JUMP, result4);

    }

    @Test
    public void testGetMoveTypeInvalid() {
        PostValidateMoveRoute.MoveType result = CuT.getMoveType(5, 5);
        assertEquals(PostValidateMoveRoute.MoveType.INVALID, result);
    }


}
