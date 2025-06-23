package com.example.model;

public class Board {

    private final int dimension = 8;

    private Piece[][] board = new Piece[dimension][dimension];

    /**
     * //we create a method to initialize the baord, every piece must be on a black cell of the board {black cell = (row+col)%2 !=0}
     */
    public Board(){
        for(int row=0;row<3;row++){
            for(int col=0;col<dimension;col++){
                if((row+col) % 2 !=0){
                    board[row][col] = new Piece(PieceColor.BLACK);
                }else{
                    board[row][col] = null;
                }
                
            }
        }
        
         for(int row=dimension-1;row>=5;row--){
            for(int col=0;col<dimension;col++){
                if((row+col) % 2 !=0){
                    board[row][col] = new Piece(PieceColor.WHITE);
                }else{
                    board[row][col] = null;
                }
            }  
        }

}


/**
 * @param row the rows of the board
 * @param col the columns of the board
 * @return  we use this to get a piece from the board at a specified location
 */
public Piece getPiece(int row, int col){
    return board[row][col];
}


/**
 * @param row the rows of the board
 * @param col the columns of the board
 * @param p we use this to set a piece on the board at a specified location
 */
public void setPiece(int row, int col, Piece p){
    board[row][col] = p;
}


/**
 * @param row
 * @param col
 * @return  if a cell is empty
 */
public boolean isEmpty(int row,int col){
    return board[row][col] == null;
}


/**
 * @param row
 * @param col
 * @return  we use this to verify the player's choice
 */
public boolean isValid(int row,int col){
    return (row>=0 && col<dimension  && row<dimension && col>=0);
     
}


public int getSize(){
    return this.dimension;
}


public void removePiece(int row,int col){
    board[row][col] = null;
}

}