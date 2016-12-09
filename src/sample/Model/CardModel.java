package sample.Model;

import sample.Enumeration.TypeCard;

/**
 * Created by theo on 07/12/16.
 */
public class CardModel implements Comparable<CardModel>{
    private int numero;
    private TypeCard color;
    private boolean inDog;


    public CardModel(int numero, TypeCard color){
        this.numero = numero;
        this.color = color;
        inDog = false;
    }

    public int getNumero() {
        return numero;
    }

    public TypeCard getColor() {
        return color;
    }
    public boolean isInDog() {
        return inDog;
    }

    public void setInDog(boolean inDog) {
        this.inDog = inDog;
    }

    /* Compare les cartes selon leur numéro, pour les trier du plus petit au plus grand */
    @Override
    public int compareTo(CardModel c){
        return this.numero-c.numero;
    }
}
