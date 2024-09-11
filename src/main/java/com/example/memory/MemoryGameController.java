package com.example.memory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;


import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

//Controller della logica di gioco, Initializable è un'interfaccia di JavaFX usata per definire un comportamento di inizializzazione per i controller associati al file FXML
public class MemoryGameController implements Initializable {

    @FXML
    private Label correctLabel;

    @FXML
    private Label guessesLabel;

    @FXML
    private FlowPane imagesFlowPane;

    private ArrayList<MemoryCard> cardsInGame;

    //Carte selezionate al momento
    private MemoryCard firstCard, secondCard;

    private int numOfGuesses;

    private int numOfMatches;

    @FXML
    void startGame() {
        firstCard = null;
        secondCard = null;
        DeckOfCards deck = new DeckOfCards();
        deck.shuffle();
        cardsInGame = new ArrayList<>();

        //Pesca 5 tipi di carte diverse (siccome ho 5 coppie) da sopra al masso e le mette in gioco
        for (int i = 0; i < imagesFlowPane.getChildren().size() / 2; i++) {
            Card cardDealt = deck.dealTopCard();
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
        }
        //Le mescola e gira in modo che compaia il retro
        Collections.shuffle(cardsInGame);
        flipAllCards();
    }

    @FXML
    //Uguale a start ma resetta le labels
    void restartGame() {
        firstCard = null;
        secondCard = null;
        DeckOfCards deck = new DeckOfCards();
        deck.shuffle();
        cardsInGame = new ArrayList<>();

        for (int i = 0; i < imagesFlowPane.getChildren().size() / 2; i++) {
            Card cardDealt = deck.dealTopCard();
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
        }
        Collections.shuffle(cardsInGame);
        flipAllCards();
        //L'unica cosa che cambia col restart è il reset delle label
        numOfMatches = 0;
        numOfGuesses = 0;
        updateLabels();
    }

    @Override
    //Metodo chiamato in automatico quando il file FXML è stato caricatro e gli elementi della GUI sono stati creati
    public void initialize(URL location, ResourceBundle resources) {
        //Associa ciascuna carta della GUI al suo comportamento e immagine
        initializeImageView();
        //Avvia una nuova partita
        startGame();
    }

    //Ogni carta del FlowPane (del tavolo di gioco) viene girata in modo che mostri il retro
    private void initializeImageView() {
        for(int i = 0; i < imagesFlowPane.getChildren().size(); i++) {
            ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(i);
            imageView.setImage(new Image(Card.class.getResourceAsStream("images/back_of_card.png")));
            //Viene assegnato un identificativo ad ogni carta
            imageView.setUserData(i);

            //Click listener che si attiva quando clicco una carta, attiva il metodo flipCard()
            imageView.setOnMouseClicked(event -> { flipCard((int) imageView.getUserData()); });
        }
    }

    //Gestisce la logica di quando una carta viene cliccata
    private void flipCard(int indexOfCard) {
        //Se non ci sono carte selezionate chiama flipAllCards()
        if (firstCard == null && secondCard == null) {
            flipAllCards();
        }
        //Usa l'indice della carta per recuperarla dalla lista di carte in gioco
        ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(indexOfCard);

        //La carta cliccata è la prima selezionata, setta l'imageView per mostrarne la faccia
        if (firstCard==null) {
            firstCard = cardsInGame.get(indexOfCard);
            imageView.setImage(firstCard.getImage());
        } else if (secondCard==null) {
            //La carta cliccata è la seconda selezionata, mostro l'immagine e verifico se sono uguali
            numOfGuesses++;
            secondCard = cardsInGame.get(indexOfCard);
            imageView.setImage(secondCard.getImage());
            checkForMatch();
            updateLabels();
        }
    }

    //Aggiorna l'immagine visualizzata per le carte in base al loro stato, matched o meno
    private void flipAllCards() {
        for (int i = 0; i < cardsInGame.size(); i++) {
            ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(i);
            MemoryCard card = cardsInGame.get(i);

            if (card.isMatched()) {
                imageView.setImage(card.getImage());
            } else {
                imageView.setImage(card.getBackOfCardImage());
            }
        }
    }

    //Verifica se 2 carte sono uguali, se lo sono le segna come matched e aggiorna il numero di match
    private void checkForMatch() {
        if (firstCard.isSameCard(secondCard)) {
            numOfMatches++;
            firstCard.setMatched(true);
            secondCard.setMatched(true);
        }
        firstCard=null;
        secondCard=null;
    }

    //Aggiorna le labels
    private void updateLabels() {
        correctLabel.setText(Integer.toString(numOfMatches));
        guessesLabel.setText(Integer.toString(numOfGuesses));
    }

}
