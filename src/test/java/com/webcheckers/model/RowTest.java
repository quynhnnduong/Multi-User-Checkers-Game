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
    private Piece.Color bottomColor;   //not really sure what this is but I'm putting a random color for now


    @BeforeEach
    public void setUp(){
        index = 0;
        leadingSpaceColor = SpaceColor.BLACK;
        bottomColor = Piece.Color.RED;
    }

    @Test
    public void testGenerateRowLeadingColor(){
        // Setup
        CuT = new Row(index, leadingSpaceColor, bottomColor);

        // Invoke
        ArrayList<Space> row = CuT.generateRow(leadingSpaceColor, bottomColor);

        // Analyze
        assertEquals(leadingSpaceColor, row.get(0).getColor());
    }

    @Test
    public void testGenerateRowSize(){
        // Setup
        CuT = new Row(index, leadingSpaceColor, bottomColor);

        // Invoke
        ArrayList<Space> row = CuT.generateRow(leadingSpaceColor, bottomColor);

        // Analyze
        assertEquals(row.size(), 8);

    }

    @Test
    public void testCreateRow(){
        // Invoke
        CuT = new Row(index, leadingSpaceColor, bottomColor);

        // Analyze
        assertNotNull(CuT);
    }

    @Test
    public void testGetSpacesSize(){
        // Setup
        CuT = new Row(index, leadingSpaceColor, bottomColor);

        // Invoke
        ArrayList<Space> spaces = CuT.getSpaces();

        // Analyze
        assertEquals(spaces.size(), 8);
    }

    @Test
    public void testGetSpacesLeadingColor(){
        // Setup
        CuT = new Row(index, leadingSpaceColor, bottomColor);

        // Invoke
        ArrayList<Space> spaces = CuT.getSpaces();

        // Analyze
        assertEquals(spaces.get(0).getColor(), leadingSpaceColor);
    }

    @Test
    public void testGetIndex(){
        // Setup
        CuT = new Row(index, leadingSpaceColor, bottomColor);

        // Invoke
        ArrayList<Space> row = CuT.generateRow(leadingSpaceColor, bottomColor);

        // Analyze
        assertEquals(CuT.getIndex(), 0);
    }

}
