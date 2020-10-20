package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit Tests for {@link Row} Component
 * @author Sasha Persaud
 */
@Tag("Model-tier")
public class RowTest {
    // Component under test
    private Row CuT;

    // Friendlies
    private int index;
    private ArrayList<Space> row;
    private SpaceColor leadingSpaceColor;


    @BeforeEach
    public void setUp(){
        index = 0;
        leadingSpaceColor = SpaceColor.BLACK;
    }

   // @Test
    public void testGenerateRowLeadingColor(){ // TODO Fix this test (Fails)
        // Setup
        CuT = mock(Row.class);

        // Invoke
        row = CuT.generateRow(leadingSpaceColor);

        // Analyze
        assertEquals(leadingSpaceColor, row.get(0).getColor());
    }

   // @Test
    public void testGenerateRowSize(){ // TODO Fix this test (AssertionError: expect 8, actual 0)
        // Setup
        CuT = mock(Row.class);

        // Invoke
        ArrayList<Space> generatedRow = CuT.generateRow(leadingSpaceColor);

        // Analyze
        assertEquals(8, generatedRow.size());
    }

    @Test
    public void testCreateRow(){
        // Invoke
        CuT = new Row(index, leadingSpaceColor);

        // Analyze
        assertNotNull(CuT);
    }

    @Test
    public void testGetSpacesSize(){
        // Setup
        CuT = new Row(index, leadingSpaceColor);

        // Invoke
        ArrayList<Space> spaces = CuT.getSpaces();

        // Analyze
        assertEquals(spaces.size(), 8);
    }

    @Test
    public void testGetSpacesLeadingColor(){
        // Setup
        CuT = new Row(index, leadingSpaceColor);

        // Invoke
        ArrayList<Space> spaces = CuT.getSpaces();

        // Analyze
        assertEquals(spaces.get(0).getColor(), leadingSpaceColor);
    }

    @Test
    public void testFlipRow(){
        //Setup
        CuT = new Row(index, leadingSpaceColor);
        int newIndex = 1;

        // Invoke
        Row newRow = CuT.flipRow(newIndex);

        // Analyze - the leading color of the flipped row should not be the same as the original
        assertNotEquals(newRow.getSpaces().get(0).getColor(), CuT.getSpaces().get(0).getColor());
    }
}
