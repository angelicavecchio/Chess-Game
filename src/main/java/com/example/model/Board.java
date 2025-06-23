package com.example.model;

public class Board {

    private final int dimension = 8;

    private Piece[][] board = new Piece[dimension][dimension];

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


public Piece getPiece(int row, int col){
    return board[row][col];
}


public void setPiece(int row, int col, Piece p){
    board[row][col] = p;
}


public boolean isEmpty(int row,int col){
    return board[row][col] == null;
}

public boolean isValid(int row,int col){
    return (row>=0 && col<dimension  && row<dimension && col>=0);
     
}

}