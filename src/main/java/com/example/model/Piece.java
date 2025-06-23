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


    public boolean isKing(Piece p){
        if(p.type != PieceType.KING){
        return false;
    }
        return true;
    }



    public void setKing(Piece p){
        p.type = PieceType.KING;
    }


    }





