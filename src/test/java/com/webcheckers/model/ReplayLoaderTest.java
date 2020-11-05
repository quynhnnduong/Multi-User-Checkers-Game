package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Model-tier")

public class ReplayLoaderTest {

    // Component under Test
    private ReplayLoader CuT;

    // Friendlies
    private ReplaySaver replaySaver;
    private HashMap<String, Replay> replayList;


    @BeforeEach
    public void setUp() {
        replaySaver = mock(ReplaySaver.class);
        replayList = mock(HashMap.class);

        CuT = new ReplayLoader(replaySaver);
    }

    @Test
    public void testIncrementTurn(){
        when(replaySaver.getAllReplays()).thenReturn(replayList);
        when(replayList.size()).thenReturn(3);
        CuT = new ReplayLoader(replaySaver);

        assertTrue(CuT.incrementTurn());
    }

    @Test
    public void testNotIncrementTurn(){
        when(replaySaver.getAllReplays()).thenReturn(replayList);
        when(replayList.size()).thenReturn(1);
        CuT = new ReplayLoader(replaySaver);

        assertFalse(CuT.incrementTurn());
    }

    @Test
    public void testDecrementTurn(){
        when(replaySaver.getAllReplays()).thenReturn(replayList);
        when(replayList.size()).thenReturn(3);
        CuT = new ReplayLoader(replaySaver);
        CuT.incrementTurn();

        assertTrue(CuT.decrementTurn());
    }

    @Test
    public void testNotDecrementTurn(){
        when(replaySaver.getAllReplays()).thenReturn(replayList);
        when(replayList.size()).thenReturn(1);
        CuT = new ReplayLoader(replaySaver);
        CuT.incrementTurn();

        assertFalse(CuT.decrementTurn());
    }


}
