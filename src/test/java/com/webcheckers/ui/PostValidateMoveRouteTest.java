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
import static org.junit.jupiter.api.Assertions.*;
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
    private BoardView curView;
    private Game.ActiveColor curColor;
    private Piece piece;

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
        curView = mock(BoardView.class);
        curColor = Game.ActiveColor.RED;
        piece = mock(Piece.class);

        when(request.session()).thenReturn(session);

        CuT = new PostValidateMoveRoute(gameCenter);
        gson = new Gson();

    }

    @Test
    public void testHandle_ForwardSingle() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":-1,\"cell\":1}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test True/True
        when(turn.hasMoves()).thenReturn(true);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: A jump move can be taken this turn.")), validMessage);
    }

    @Test
    public void testHandle_ForwardJump() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":-2,\"cell\":2}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test True/True
        when(turn.hasMoves()).thenReturn(true);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Not a valid jump move.")), validMessage);
    }

    @Test
    public void testHandle_BackwardSingleInvalid() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":1,\"cell\":1}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test True/True
        when(turn.hasMoves()).thenReturn(true);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);

        // Set up the case conditionals
        when(curView.getPiece(anyInt(), anyInt())).thenReturn(piece);
        when(piece.isKing()).thenReturn(false);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Only kings can move backwards")), validMessage);
    }

    @Test
    public void testHandle_BackwardSingleValid() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":1,\"cell\":1}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test True/True
        when(turn.hasMoves()).thenReturn(true);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);

        // Set up the case conditionals
        when(curView.getPiece(anyInt(), anyInt())).thenReturn(piece);
        when(piece.isKing()).thenReturn(true);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: A jump move can be taken this turn.")), validMessage);
    }

    @Test
    public void testHandle_BackwardJumpInvalid() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":2,\"cell\":2}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test True/True
        when(turn.hasMoves()).thenReturn(true);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);

        // Set up the case conditionals
        when(curView.getPiece(anyInt(), anyInt())).thenReturn(piece);
        when(piece.isKing()).thenReturn(false);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Only kings can move backwards")), validMessage);
    }

    @Test
    public void testHandle_BackwardJumpValid_Right() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":2,\"cell\":2}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test True/True
        when(turn.hasMoves()).thenReturn(true);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);

        // Set up the case conditionals
        when(curView.getPiece(anyInt(), anyInt())).thenReturn(piece);
        when(piece.isKing()).thenReturn(true);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Not a valid jump move.")), validMessage);
    }

    @Test
    public void testHandle_BackwardJumpValid_Left() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":2,\"cell\":-2}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test True/True
        when(turn.hasMoves()).thenReturn(true);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);

        // Set up the case conditionals
        when(curView.getPiece(anyInt(), anyInt())).thenReturn(piece);
        when(piece.isKing()).thenReturn(true);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Not a valid jump move.")), validMessage);
    }

    @Test
    public void testHandle_InvalidMove() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":3,\"cell\":3}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test True/True
        when(turn.hasMoves()).thenReturn(true);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Not diagonal")), validMessage);
    }

    @Test
    public void testHandle_NoMoveMade() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":3,\"cell\":3}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test False/True
        when(turn.hasMoves()).thenReturn(false);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(true);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Not diagonal")), validMessage);
    }

    @Test
    public void testHandle_NoRequiredMove() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":3,\"cell\":3}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test True/False
        when(turn.hasMoves()).thenReturn(false);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(false);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Not diagonal")), validMessage);
    }

    @Test
    public void testHandle_NoMoveMade_NoRequiredMove() {
        //  Set up
        String messageText = "{\"start\": { \"row\": 0,\"col\": 0}, \"end\":{\"row\":3,\"cell\":3}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // mock the turn and board
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(game.getActiveColor()).thenReturn(curColor);
        when(game.getActivePlayerBoard()).thenReturn(curView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);

        // Set up the conditional to test False/False
        when(turn.hasMoves()).thenReturn(false);
        when(curView.isRequiredToJump(curColor, turn)).thenReturn(false);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Not diagonal")), validMessage);
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
