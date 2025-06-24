package com.example.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class PrimaryController implements Initializable{

    @FXML
    private GridPane board;
    
    @FXML
    private Label blackPiecesLabel;
    
    @FXML
    private Label whitePiecesLabel;
    
    @FXML
    private Label gameOverLabel;
    
    @FXML
    private Label turnPlayerLabel;
    
    @FXML
    private Button passButton;
    
    
    // ==================== VARIABILI DI GIOCO ====================
    
    private GameLogic gameLogic;
    private Board gameBoard;
    
    private Pane selectedCell = null;
    private Circle selectedPiece = null;
    private boolean isWhiteTurn = false; // Il nero inizia sempre
    
    
    // ==================== INIZIALIZZAZIONE ====================
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Controller inizializzato!");
        
        // Inizializza il gioco
        initializeGame();
        
        // Aggiorna l'interfaccia
        updateUI();
    }
    
    private void initializeGame() {
        // Qui inizializzerai le tue classi del model
        gameLogic = new GameLogic();
        gameBoard = new Board();
        
        // Per ora simuliamo
        System.out.println("Gioco inizializzato - Tocca al NERO");
    }
    
    
    // ==================== GESTIONE EVENTI ====================
    
    @FXML
    private void onCellClicked(MouseEvent event) {
        System.out.println("Cella cliccata!"); //Debug
        
        // Ottieni la cella cliccata
        Pane clickedCell = (Pane) event.getSource();
        
        // Estrai le coordinate dalla fx:id (es: "cell_2_3" -> riga=2, colonna=3)
        String cellId = clickedCell.getId();
        int[] coordinates = extractCoordinates(cellId);
        int row = coordinates[0];
        int col = coordinates[1];
        
        System.out.println("Coordinate: riga=" + row + ", colonna=" + col);
        
        // Verifica se c'è un pezzo nella cella
        Circle piece = getPieceInCell(clickedCell);
        
        if (selectedCell == null) {
            // PRIMO CLICK: seleziona un pezzo
            handleFirstClick(clickedCell, piece, row, col);
        } else {
            // SECONDO CLICK: muovi il pezzo o deseleziona
            handleSecondClick(clickedCell, piece, row, col);
        }
    }
    
    private void handleFirstClick(Pane clickedCell, Circle piece, int row, int col) {
        if (piece == null) {
            System.out.println("Cella vuota - nessuna selezione");
            return;
        }
        
        // Verifica se il pezzo appartiene al giocatore corrente
        boolean isPieceWhite = isPieceWhite(piece);
        
        if (isPieceWhite != isWhiteTurn) {
            System.out.println("Non è il turno di questo giocatore!");
            return;
        }
        
        // Seleziona il pezzo
        selectPiece(clickedCell, piece);
        System.out.println("Pezzo selezionato in posizione: " + row + "," + col);
    }
    
    private void handleSecondClick(Pane clickedCell, Circle piece, int row, int col) {
        if (clickedCell == selectedCell) {
            // Click sulla stessa cella - deseleziona
            deselectPiece();
            System.out.println("Pezzo deselezionato");
            return;
        }
        
        if (piece != null) {
            // C'è già un pezzo - verifica se è dello stesso giocatore
            boolean isPieceWhite = isPieceWhite(piece);
            if (isPieceWhite == isWhiteTurn) {
                // Seleziona il nuovo pezzo dello stesso giocatore
                selectPiece(clickedCell, piece);
                System.out.println("Nuovo pezzo selezionato");
                return;
            }
        }
        
        // Tentativo di mossa
        attemptMove(clickedCell, row, col);
    }
    
    private void attemptMove(Pane targetCell, int targetRow, int targetCol) {
        // Ottieni coordinate della cella selezionata
        String selectedCellId = selectedCell.getId();
        int[] selectedCoords = extractCoordinates(selectedCellId);
        int fromRow = selectedCoords[0];
        int fromCol = selectedCoords[1];
        
        System.out.println("Tentativo mossa da " + fromRow + "," + fromCol + 
                          " a " + targetRow + "," + targetCol);
        
        // QUI CHIAMERAI IL TUO MODEL per validare la mossa
        // boolean isValidMove = gameLogic.isValidMove(fromRow, fromCol, targetRow, targetCol);
        
        // Per ora simuliamo una mossa valida
        boolean isValidMove = isSimpleValidMove(fromRow, fromCol, targetRow, targetCol);
        
        if (isValidMove) {
            // Esegui la mossa
            executeMove(fromRow, fromCol, targetRow, targetCol, targetCell);
        } else {
            System.out.println("Mossa non valida!");
        }
        
        // Deseleziona sempre
        deselectPiece();
    }
    
    @FXML
    private void handlePassButton(ActionEvent event) {
        System.out.println("Bottone PASS premuto");
        
        // Cambia turno
        switchTurn();
        
        // Aggiorna UI
        updateUI();
    }
    
    
    // ==================== METODI DI SUPPORTO ====================
    
    private int[] extractCoordinates(String cellId) {
        // Estrae riga e colonna da una stringa come "cell_2_3"
        String[] parts = cellId.split("_");
        int row = Integer.parseInt(parts[1]);
        int col = Integer.parseInt(parts[2]);
        return new int[]{row, col};
    }
    
    private Circle getPieceInCell(Pane cell) {
        // Cerca un Circle (pezzo) dentro la cella
        return cell.getChildren().stream()
                  .filter(node -> node instanceof Circle)
                  .map(node -> (Circle) node)
                  .findFirst()
                  .orElse(null);
    }
    
    private boolean isPieceWhite(Circle piece) {
        // Determina il colore del pezzo dal fill o dall'ID
        String pieceId = piece.getId();
        return pieceId != null && pieceId.startsWith("white");
    }
    
    private void selectPiece(Pane cell, Circle piece) {
        // Deseleziona precedente
        deselectPiece();
        
        // Seleziona nuovo
        selectedCell = cell;
        selectedPiece = piece;
        
        // Evidenzia visualmente (aggiungi classe CSS)
        cell.getStyleClass().add("selected-cell");
        piece.getStyleClass().add("selected-piece");
    }
    
    private void deselectPiece() {
        if (selectedCell != null) {
            // Rimuovi evidenziazione
            selectedCell.getStyleClass().remove("selected-cell");
            selectedCell = null;
        }
        
        if (selectedPiece != null) {
            selectedPiece.getStyleClass().remove("selected-piece");
            selectedPiece = null;
        }
    }
    
    private boolean isSimpleValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Validazione semplificata per ora
        // Nella dama si muove solo in diagonale
        
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        // Movimento diagonale di una cella
        return rowDiff == 1 && colDiff == 1;
    }
    
    private void executeMove(int fromRow, int fromCol, int toRow, int toCol, Pane targetCell) {
        System.out.println("Eseguendo mossa...");
        
        // Rimuovi il pezzo dalla cella originale
        selectedCell.getChildren().remove(selectedPiece);
        
        // Aggiungi il pezzo alla cella di destinazione
        targetCell.getChildren().add(selectedPiece);
        
        // Cambia turno
        switchTurn();
        
        // Aggiorna UI
        updateUI();
        
        System.out.println("Mossa completata!");
    }
    
    private void switchTurn() {
        isWhiteTurn = !isWhiteTurn;
    }
    
    private void updateUI() {
        // Aggiorna il turno
        if (isWhiteTurn) {
            turnPlayerLabel.setText("Turno: BIANCO");
        } else {
            turnPlayerLabel.setText("Turno: NERO");
        }
        
        // Conta i pezzi (metodo semplificato)
        int blackCount = countPieces("black");
        int whiteCount = countPieces("white");
        
        blackPiecesLabel.setText(String.valueOf(blackCount));
        whitePiecesLabel.setText(String.valueOf(whiteCount));
        
        // Nascondi il game over per ora
        gameOverLabel.setVisible(false);
        
        System.out.println("UI aggiornata - Turno: " + (isWhiteTurn ? "BIANCO" : "NERO"));
    }
    
    private int countPieces(String color) {
        // Conta tutti i pezzi del colore specificato nella griglia
        int count = 0;
        
        for (var node : board.getChildren()) {
            if (node instanceof Pane) {
                Pane cell = (Pane) node;
                Circle piece = getPieceInCell(cell);
                if (piece != null && piece.getId() != null && piece.getId().startsWith(color)) {
                    count++;
                }
            }
        }
        
        return count;
    }
}