package com.webcheckers.model;

/**
 * Model class to represent the state of the checker board
 * Currently just an empty board
 * @author Quynh Duong
 */
public class Board {

    //Create a board using the Space Object
    private Space board[][];
    private Piece.getColor currentPlayerColor;

    /**
     * Initialize a  checker board
     */
    public Board(){
        //Initialize the board with the given size from BoardView
        board = new Space[BoardView.BOARD_SIZE][BoardView.BOARD_SIZE];
        createBoard();
    }

    /**
     * Creates a blank checker board
     */
    public void createBoard(){
        for(int i = 0; i < BoardView.BOARD_SIZE; i++)
            for(int j = 0; j < BoardView.BOARD_SIZE; j++)
                board[i][j] = new Space(j, null);
        currentPlayerColor = null;
    }

    /**
     * Gets the space at the specific row,col
     * @param row The row of the space
     * @param col The col of the space
     * @return The space
     */
    public Space getSpaceIndex(int row, int col) {
        return board[row][col];
    }
}
