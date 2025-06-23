package com.example.model;

public class GameLogic {

    private Board board;
    private Player player1;
    private Player player2;
    private Player current_player;
    private GAME_STATE state;



    public GameLogic(){
        this.board = new Board();
        this.player1 = new Player();
        this.player2 = new Player();
        this.current_player = player1;
        this.state = GAME_STATE.IN_PROGRESS;
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol){
        if(board.isEmpty(fromRow, fromCol) || !board.isValid(fromRow, fromCol) ){
            return false;
        }

        if (!board.isEmpty(toRow, toCol)) {
          return false; 
        }

        if(!board.isValid(toRow, toCol)){
            return false;
        }
        
        Piece piece = board.getPiece(fromRow, fromCol);
        if(piece.getColor() != current_player.getColor()){
            return false;
        }
        
        if(Math.abs(fromRow-toRow) != Math.abs(fromCol-toCol)){
            return false;
        }

        if (Math.abs(fromRow - toRow) != 1) {
        return false;
           }

           if (Math.abs(fromCol - toCol) != 1) {
           return false;
         }
        
        if(!piece.isKing()){
            if(piece.getColor()== PieceColor.WHITE && toRow>=fromRow){
                return false; //white can only go up 
            }

            if(piece.getColor()== PieceColor.BLACK && toRow<=fromRow){
                return false; //black must go down 
            }
        }
            
        
        return true;    
    }


       public void switchTurn(){
        if(current_player==player1){
            current_player=player2;
        }else{
            current_player=player1;
        }
       }


       public void makeMove(int fromRow, int fromCol, int toRow, int toCol){
        if(isValidMove(fromRow, fromCol, toRow, toCol)){
            Piece piece = board.getPiece(fromRow, fromCol);
            board.setPiece(toRow, toCol, piece);
            board.removePiece(fromRow,fromCol);

          
                if(piece.getColor()== PieceColor.WHITE && toRow==0){
                    piece.setKing();
                }
                if(piece.getColor()== PieceColor.BLACK && toRow==board.getSize()-1){
                    piece.setKing();
                }

                switchTurn();
            }
            
        }
       }





