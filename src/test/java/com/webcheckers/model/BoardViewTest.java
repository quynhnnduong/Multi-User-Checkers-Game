package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.webcheckers.model.BoardView.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardViewTest {
    /**
     * Test that the constructor works with no arg
     */
    @Test
    public void bv_noArg(){new BoardView(Piece.Color.RED);}

    /**
     * Test that the constructor works with row[] arg
     */
    @Test
    public void bv_rowArr(){new BoardView(new ArrayList<Row>());}

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
}

