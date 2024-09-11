package com.example.memory;

public class MemoryCard extends Card{
    //Indica se ho trovato la sua coppia
    private boolean matched;

    public MemoryCard(String suit, String faceName) {
        super(suit, faceName);
        this.matched = false;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    //Controlla se 2 carte MemoryCards sono identiche
    public boolean isSameCard(MemoryCard otherCard) {
        return (this.getSuit().equals(otherCard.getSuit()) && (this.getFaceName().equals(otherCard.getFaceName())));
    }
}
