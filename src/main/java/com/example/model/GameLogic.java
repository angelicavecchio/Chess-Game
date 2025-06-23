package com.example.model;

public class GameLogic {

    private Board board;
    private Player player1;
    private Player player2;
    private Player current_player;
    private GAME_STATE state;



    public GameLogic(){
        this.board = new Board();
        this.player1 = new Player("Giocatore1", PieceColor.WHITE);
        this.player2 = new Player("Giocatore2", PieceColor.BLACK);
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

        if (Math.abs(fromRow - toRow) == 1) { 
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

           if (Math.abs(fromRow - toRow) == 2) {
           if(board.isEmpty((fromRow+toRow)/2, (fromCol+toCol)/2)){
                return false;
            }

            if(board.getPiece((fromRow+toRow)/2, (fromCol+toCol)/2).getColor()==current_player.getColor()){
                return false;
            }
            if(piece.getColor()== PieceColor.WHITE && toRow>=fromRow){
                return false; //white can only go up 
            }

            if(piece.getColor()== PieceColor.BLACK && toRow<=fromRow){
                return false; //black must go down 
            }
            return true;  
         
        }

        
        
        return false;
          
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
            if(Math.abs(fromRow - toRow) == 2) {
   
            board.removePiece((fromRow+toRow)/2, (fromCol+toCol)/2);
            current_player.pieces--;
   
           }
     
            

          
                if(piece.getColor()== PieceColor.WHITE && toRow==0){
                    piece.setKing();
                }
                if(piece.getColor()== PieceColor.BLACK && toRow==board.getSize()-1){
                    piece.setKing();
                }

                switchTurn();
            }
            
        }

        public boolean isGameOver() {
    
            if(player1.getNumberPieces()==0  || player2.getNumberPieces()==0){
                return true;
            }

            if(!playerHasMoves(player1)){
               state = GAME_STATE.BLACK_WINS;
                return true;
            }
            if(!playerHasMoves(player2)){
            state = GAME_STATE.WHITE_WINS;
                return true;
            }
            

               return false;
            }


           public boolean playerHasMoves(Player player) {
           for (int row = 0; row < board.getSize(); row++) {
           for (int col = 0; col < board.getSize(); col++) {
            Piece piece = board.getPiece(row, col);
            if (piece != null && piece.getColor() == player.getColor()) {
                int[] directions = {-1, 1}; 

                for (int dRow : directions) {
                    for (int dCol : directions) {
                        int newRow1 = row + dRow;
                        int newCol1 = col + dCol;
                        int newRow2 = row + 2 * dRow;
                        int newCol2 = col + 2 * dCol;

                        if (board.isValid(newRow1, newCol1) &&
                            isValidMove(row, col, newRow1, newCol1)) {
                            return true;
                        }

                        if (board.isValid(newRow2, newCol2) &&
                            isValidMove(row, col, newRow2, newCol2)) {
                            return true; 
                        }
                    }
                }
            }
        }
    }
    return false; 
}

}







       





