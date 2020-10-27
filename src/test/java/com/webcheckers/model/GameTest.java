//package com.webcheckers.model;
//
//import com.webcheckers.model.Game;
//import com.webcheckers.model.Player;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Unit Tests for {@link Game} Component
// * @author Sasha Persaud
// */
////@Tag("Application-tier")
//public class GameTest {
//
//    // Component under test
//    private Game CuT;
//
//    // Friendlies
//    private Player redPlayer;
//    private Player whitePlayer;
//    private int id;
//
////    @BeforeEach
//    private void setUp(){
//        redPlayer = new Player("RedPlayer");
//        whitePlayer = new Player("WhitePlayer");
//        id = 1234;
//    }
//
////    @Test
//    public void testCreateGame(){
//        // Invoke
//        CuT = new Game(redPlayer, whitePlayer, id);
//
//        // Analyze
//        assertNotNull(CuT);
//    }
//
////    @Test
//    public void testGetRedPlayer(){
//        // Invoke
//        CuT = new Game(redPlayer, whitePlayer, id);
//        Player testPlayer = CuT.getRedPlayer();
//
//        // Analyze
//        assertEquals(redPlayer, testPlayer);
//    }
//
////    @Test
//    public void testGetWhitePlayer(){
//        // Invoke
//        CuT = new Game(redPlayer, whitePlayer, id);
//        Player testPlayer = CuT.getWhitePlayer();
//
//        // Analyze
//        assertEquals(whitePlayer, testPlayer);
//    }
//
////    @Test
//    public void testGetId(){
//        // Invoke
//        CuT = new Game(redPlayer, whitePlayer, id);
//        int testID = CuT.getId();
//
//        // Analyze
//        assertEquals(id, testID);
//    }
//}
