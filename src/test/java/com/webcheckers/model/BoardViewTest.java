package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.webcheckers.model.BoardView.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit Tests for {@link BoardView} Component
 *
 * @author  (?) ,Shubhang Mehrotra
 */
@Tag("Model-tier")
public class BoardViewTest {

    //friendlies
    private Space space;

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
    public void testMakeMove(){
        // Setup
        BoardView bv = new BoardView(Piece.Color.RED);

        //Invoke
        bv.generateBoard(Piece.Color.RED);
    }

    @Test
    public void testIterator(){
        // Setup
        BoardView bv = new BoardView(Piece.Color.RED);

        //Invoke
        bv.generateBoard(Piece.Color.RED);
        Position start = new Position(0,0);
        Position end = new Position(0,0);
        Move move = new Move(start, end);

        //Analyze
//        assertNotNull(bv.makeMove(move));

    }

}

