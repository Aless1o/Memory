package com.example.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DeckOfCards {
    private ArrayList<Card> deck;

    //Nel costruttore vengono combinati semi e nomi delle facce delle carte
    public DeckOfCards() {
        this.deck = new ArrayList<>();
        List<String> suits = Card.getValidSuits();
        List<String> faceNames = Card.getValidFaceNames();

        for(String suit : suits) {
            for(String faceName : faceNames) {
                deck.add(new Card(suit, faceName));
            }
        }
    }

    //Mischia il mazzo in modo che non siano in ordine di seme e numerico
    public void shuffle() {
        Collections.shuffle(deck);
    }

    //Restituisce la carta in cima e la rimuove, se il mazzo è vuoto riorna null
    public Card dealTopCard() {
        if(deck.size() > 0) {
            return deck.remove(0);
        } else {
            return null;
        }
    }

    //Numero di carte rimanenti
    public int getNumOfCards() {
        return deck.size();
    }
}
