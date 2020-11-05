package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ClassUtils;

import java.security.PublicKey;
import java.util.ArrayList;

import static com.webcheckers.model.BoardView.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Tests for {@link BoardView} Component
 *
 * @author  (?) ,Shubhang Mehrotra
 */
@Tag("Model-tier")
public class BoardViewTest {

    //friendlies
    private Space space;
    private ArrayList<Row> board;

    // Component Under Test
    private BoardView CuT;
    private Piece.Color color;
    private Move move;
    private Position start, end;
    private Space startSpace, endSpace;
    private Piece piece;

    @BeforeEach
    public void setUp() {
        // mocks
        move = mock(Move.class);
        start =  mock(Position.class);
        end =  mock(Position.class);
        startSpace = mock(Space.class);
        endSpace = mock(Space.class);
        piece = mock(Piece.class);

        // friendlies
        color = Piece.Color.RED;
        board = new ArrayList<>();
        CuT = new BoardView(color);
    }

    @Test
    public void testMakeMove_SimpleMove(){
        //setup
        board = CuT.generateBoard(color);

        //sample position for simple move
        this.start = new Position(5,0);
        Space startSpace = new Space(0, SpaceColor.BLACK);
        Piece piece = startSpace.getPiece();
        this.end = new Position(4,1);
        Space endSpace = new Space(1, SpaceColor.BLACK);
        this.move = mock(Move.class);

        when(move.isJumpMove()).thenReturn(false);
        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);


        CuT.makeMove(move);

        assertNull(startSpace.getPiece());
        assertEquals(endSpace.getPiece(), piece);
    }

    //@Test
    public void testMakeMove_JumpMove_CaptureRed(){
        //setup
        board = CuT.generateBoard(color);

        this.start = new Position(5,0);
        Space startSpace = new Space(0, SpaceColor.BLACK);
        Piece piece = startSpace.getPiece();
        this.end = new Position(3,2);
        Space endSpace = new Space(2, SpaceColor.BLACK);
        this.move = mock(Move.class);

        int capturedCell = 1;
        int capturedRow = 4;
        Space capturedSpace = new Space(1, SpaceColor.BLACK);
        Piece capturedPiece = new Piece(Piece.Color.RED);


        when(move.isJumpMove()).thenReturn(true);

        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);


        CuT.makeMove(move);
        assertNull(capturedSpace.getPiece());
        assertNull(startSpace.getPiece());
        assertEquals(endSpace.getPiece(), piece);
        assertEquals(CuT.getRemainingPieces(Piece.Color.RED), 11);
    }

     //@Test
    public void testMakeMoveReplayVer(){
        // Setup
        board = CuT.generateBoard(color);
        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);


        when(start.getRow()).thenReturn(0);
        when(start.getCell()).thenReturn(0);

//        when(board.get(start.getRow()).getSpace(start.getCell())).thenReturn(startSpace);
//        when(board.get(end.getRow()).getSpace(end.getCell())).thenReturn(endSpace);


        // True/True
        when(move.getColDifference()).thenReturn(2);
        when(move.getRowDifference()).thenReturn(2);

        // True/True
        when(end.getRow()).thenReturn(0);
        when(end.getRow()).thenReturn(7);


        //Invoke
        CuT.makeMove(move);

        //Analyze


    }

    /**
     * Test that the constructor works with no arg
     */
    @Test
    public void bv_noArg(){new BoardView(Piece.Color.RED);}

    /**
     * Test that leading board spaces are the correct color
     */
    @Test
    public void bv_initColors(){
        CuT.generateBoard(Piece.Color.RED);

        SpaceColor leadingColor = SpaceColor.WHITE;
        for (int i = 0; i < BOARD_SIZE; i++){
            // Alternates the leading color in each Row to create a checker pattern
            if (i % 2 == 0) {
                //check the color of the first space
                assertEquals(SpaceColor.WHITE, CuT.getBoard().get(i).getSpaces().get(0).getColor(), "Leading space was black when it should be white");
            } else  {
                assertEquals(SpaceColor.BLACK, CuT.getBoard().get(i).getSpaces().get(0).getColor(), "Leading space was white when it should be black");
            }

        }
    }


    @Test
    public void testIterator(){
        //Invoke
        CuT.generateBoard(Piece.Color.RED);

        //Analyze
        assertNotNull(CuT.iterator());

    }

    @Test
    public void testGetRemainingPiecesRed(){
        CuT.generateBoard(color);
        assertEquals(CuT.getRemainingPieces(color), 12);
    }

    @Test
    public void testGetRemainingPiecesWhite(){
        CuT.generateBoard(color);
        assertEquals(CuT.getRemainingPieces(Piece.Color.WHITE), 12);
    }

//    @Test
//    public void testGetPiece(){
//        board = CuT.generateBoard(color);
//
//        Space space = new Space(0, SpaceColor.BLACK);
//
//
//    }

    // @Test
    public void testIsJumpPossible(){
        Piece currentPiece = mock(Piece.class);
        CuT.getPiece(1, 1);
        when(currentPiece.sameColorAs(piece)).thenReturn(false);
        assertNull(CuT.getPiece(0, 0));
        assertEquals(CuT.getPiece(1, 1), piece);

        assertTrue(CuT.isJumpPossible(currentPiece,2, 2, BoardView.JumpType.FORWARD_LEFT));
    }

    @Test
    public void testResetBoard(){
        //setup
        board = CuT.generateBoard(Piece.Color.RED);

        CuT.resetBoard();

        assertNotEquals(board, CuT.getBoard());
    }

}

