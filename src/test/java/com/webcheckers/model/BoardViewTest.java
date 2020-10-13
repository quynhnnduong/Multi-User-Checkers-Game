package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.webcheckers.model.BoardView.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardViewTest {

    private ArrayList<Row> rowArrayList;
    /**
     * Test that the constructor works with no arg
     */
    @Test
    public void bv_noArg(){new BoardView();}

    /**
     * Test that the constructor works with row[] arg
     */
    public void bv_rowArr(){new BoardView(rowArrayList);}

    /**
     * Test that leading board spaces are the correct color
     */

    public void bv_initColors(){
        BoardView bv = new BoardView();
        bv.generateBoard();

        SpaceColor leadingColor = SpaceColor.WHITE;
        for (int i = 0; i < BOARD_SIZE; i++){
            // Alternates the leading color in each Row to create a checker pattern
            if (i % 2 == 0) {
                //check the color of the first space
                assertEquals(SpaceColor.WHITE, bv.getRows().get(i).getSpaces().get(0).getColor(), "Leading space was black when it should be white");
            } else  {
                assertEquals(SpaceColor.BLACK, bv.getRows().get(i).getSpaces().get(0).getColor(), "Leading space was white when it should be black");
            }

        }
    }
}

