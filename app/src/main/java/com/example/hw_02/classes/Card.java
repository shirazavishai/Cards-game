package com.example.hw_02.classes;

public class Card {
    private int identifier;
    private int value;

    public Card(int id, int val) {
        this.identifier = id;
        this.value = val;
    }

    public Card() {
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getValue() {
        return value;
    }
}
