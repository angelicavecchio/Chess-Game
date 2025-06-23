package com.example.model;

public class Piece {

    private PieceColor color;
    private PieceType type;

    public Piece(PieceColor color){
        this.color = color;
        this.type = PieceType.NORMAL;
    }

    public PieceColor getColor(){
        return this.color;
    }
    
    
    public PieceType getType(){
        return this.type;
    }
    public void setType(PieceType type){
         this.type = type;
    }


    public boolean isKing(){
        if(this.type != PieceType.KING){
        return false;
    }
        return true;
    }



    public void setKing(){
        this.type = PieceType.KING;
    }


    }





