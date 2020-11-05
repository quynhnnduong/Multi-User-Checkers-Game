package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.webcheckers.model.BoardView.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @BeforeEach
    public void setUp() {
        // mocks
        move = mock(Move.class);
        start =  mock(Position.class);
        end =  mock(Position.class);

        // friendlies
        color = Piece.Color.RED;
        board = new ArrayList<>();
        CuT = new BoardView(color);
    }

    //@Test
    public void testMakeMove(){
        // Setup
        when(CuT.generateBoard(color)).thenReturn(board);
        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);

        when(start.getRow()).thenReturn(0);
        when(start.getCell()).thenReturn(0);

        // True/True
        when(move.getColDifference()).thenReturn(2);
        when(move.getRowDifference()).thenReturn(2);

        // True/True
        when(end.getRow()).thenReturn(0);


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
        BoardView bv = new BoardView(Piece.Color.RED);
        bv.generateBoard(Piece.Color.RED);

        SpaceColor leadingColor = SpaceColor.WHITE;
        for (int i = 0; i < BOARD_SIZE; i++){
            // Alternates the leading color in each Row to create a checker pattern
            if (i % 2 == 0) {
                //check the color of the first space
                assertEquals(SpaceColor.WHITE, bv.getBoard().get(i).getSpaces().get(0).getColor(), "Leading space was black when it should be white");
            } else  {
                assertEquals(SpaceColor.BLACK, bv.getBoard().get(i).getSpaces().get(0).getColor(), "Leading space was white when it should be black");
            }

        }
    }


    @Test
    public void testIterator(){
        // Setup
        BoardView bv = new BoardView(Piece.Color.RED);

        //Invoke
        bv.generateBoard(Piece.Color.RED);

        //Analyze
        assertNotNull(bv.iterator());

    }

}

