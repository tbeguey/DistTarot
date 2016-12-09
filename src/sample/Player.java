package sample;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by theo on 24/11/16.
 */
public class Player {
    private ArrayList<CardModel> cards = new ArrayList<>();

    public Player(){
    }

    public void addCardsToAPlayer(CardModel c){
        cards.add(c);
    }

    public void removeCardsToAPlayer(CardModel c){
        cards.remove(c);
    }

    public ArrayList<CardModel> getCards() {
        return cards;
    }
}
