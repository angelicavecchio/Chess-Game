package com.example.model;

public class Player {

    private String name;
    private PieceColor color;
    public int pieces;


    public Player(String name, PieceColor color){
        this.name = name;
        this.color = color;
        this.pieces = 12;
    }


   
    public PieceColor getColor(){
         return this.color;
    }
    public int getNumberPieces(){
         return this.pieces;
    }

}
