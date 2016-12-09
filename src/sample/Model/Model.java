package sample.Model;

import sample.Enumeration.Notification;
import sample.Enumeration.TypeCard;
import sample.Exception.LittleDryException;

import java.util.ArrayList;
import java.util.Collections;

public class Model extends java.util.Observable {
    private int idPlayerDistrib;
    private Notification notif;
    private ArrayList<CardModel> cardsDeck = new ArrayList<>();
    private ArrayList<CardModel> dog = new ArrayList();
    private ArrayList<CardModel> gap = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private boolean littleDry = false;


    public Model() {
        Player p1 = new Player();
        Player p2 = new Player();
        Player p3 = new Player();
        Player p4 = new Player();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        idPlayerDistrib = 0;
        initialiseCardsDeck();
    }

    public void initialiseCardsDeck(){
        for (int i = 1; i <= 21; i++){
            cardsDeck.add(new CardModel(i, TypeCard.Trump));
        }
        for (int i = 1; i <= 14; i++){
            cardsDeck.add(new CardModel(i,TypeCard.Diamond));
            cardsDeck.add(new CardModel(i,TypeCard.Heart));
            cardsDeck.add(new CardModel(i,TypeCard.Club));
            cardsDeck.add(new CardModel(i,TypeCard.Spade));
        }
        cardsDeck.add(new CardModel(0,TypeCard.Excuse));
        Collections.shuffle(cardsDeck); // mélange
    }


    public void addCardHand(){
        if (players.get(idPlayerDistrib).getCards().size() != 18 && cardsDeck.size() !=0){
            CardModel c = cardsDeck.get(cardsDeck.size()-1);
            players.get(idPlayerDistrib).addCardsToAPlayer(c);
            if(idPlayerDistrib == 0){
                notif = Notification.AddCardCurrentPlayer;
                setChanged();
                notifyObservers(c);
            }
            else{
                notif = Notification.AddOtherPlayer;
                setChanged();
                notifyObservers(c);
            }
            cardsDeck.remove(c);
        }
    }

    public void distribution(){
        if (players.get(0).getCards().size() == 15 && dog.size() != 6){
            addCardDog();
        }
        for (int i = 1; i <= 4; i++){
            for (int j = 1; j <= 3; j++) {
                addCardHand();
            }
            idPlayerDistrib++;
            idPlayerDistrib = idPlayerDistrib%4;
        }
        if (players.get(0).getCards().size() != 18 && players.size() != 6) {
            addCardDog();
        }
    }

    public void addCardDog() {
        CardModel c = cardsDeck.get(cardsDeck.size()-1);
        c.setInDog(true);
        this.dog.add(c);
        notif = Notification.AddDog;
        setChanged();
        notifyObservers(c);
        cardsDeck.remove(c);
    }

    public void dogToHand(){ // a tester
        for(int i=0;i<dog.size();i++){
            players.get(0).getCards().add(dog.get(i));
        }
        dog.clear();
    }

    public void sortHand(){
        ArrayList<CardModel> handSpade = new ArrayList();
        ArrayList<CardModel> handHeart = new ArrayList();
        ArrayList<CardModel> handDiamond = new ArrayList();
        ArrayList<CardModel> handClubs = new ArrayList();
        ArrayList<CardModel> handTrumps = new ArrayList();
        CardModel excuse = null;

        /* Reparti le deck du joueur dans 5 decks différents en fonction de leur couleur */
        for (int i = 0; i < players.get(0).getCards().size(); i++) {
            if (players.get(0).getCards().get(i).getColor() == TypeCard.Spade) {
                handSpade.add(players.get(0).getCards().get(i));
            } else if (players.get(0).getCards().get(i).getColor() == TypeCard.Heart) {
                handHeart.add(players.get(0).getCards().get(i));
            } else if (players.get(0).getCards().get(i).getColor() == TypeCard.Trump) {
                handTrumps.add(players.get(0).getCards().get(i));
            } else if (players.get(0).getCards().get(i).getColor() == TypeCard.Diamond) {
                handDiamond.add(players.get(0).getCards().get(i));
            } else if (players.get(0).getCards().get(i).getColor() == TypeCard.Club) {
                handClubs.add(players.get(0).getCards().get(i));
            } else if (players.get(0).getCards().get(i).getColor() == TypeCard.Excuse) {
                excuse = players.get(0).getCards().get(i);
            }
        }
        /* Vide le deck du joueur */
        players.get(0).getCards().clear();

        /* Trie les cartes selon leur nombre */
        Collections.sort(handSpade);
        Collections.sort(handHeart);
        Collections.sort(handTrumps);
        Collections.sort(handDiamond);
        Collections.sort(handClubs);

        for (int i = 0; i < handSpade.size(); i++) {
            players.get(0).getCards().add(handSpade.get(i));
            notif = Notification.AddCardCurrentPlayer;
            setChanged();
            notifyObservers(handSpade.get(i));
        }

        for (int i = 0; i < handHeart.size(); i++) {
            players.get(0).getCards().add(handHeart.get(i));
            notif = Notification.AddCardCurrentPlayer;
            setChanged();
            notifyObservers(handHeart.get(i));
        }

        for (int i = 0; i < handTrumps.size(); i++) {
            players.get(0).getCards().add(handTrumps.get(i));
            notif = Notification.AddCardCurrentPlayer;
            setChanged();
            notifyObservers(handTrumps.get(i));
        }

        if(excuse != null){
            players.get(0).getCards().add(excuse);
            notif = Notification.AddCardCurrentPlayer;
            setChanged();
            notifyObservers(excuse);
        }

        for (int i = 0; i < handDiamond.size(); i++) {
            players.get(0).getCards().add(handDiamond.get(i));
            notif = Notification.AddCardCurrentPlayer;
            setChanged();
            notifyObservers(handDiamond.get(i));
        }

        for (int i = 0; i < handClubs.size(); i++) {
            players.get(0).getCards().add(handClubs.get(i));
            notif = Notification.AddCardCurrentPlayer;
            setChanged();
            notifyObservers(handClubs.get(i));
        }

        handSpade.clear();
        handDiamond.clear();
        handTrumps.clear();
        handClubs.clear();
        handHeart.clear();
    }

    public void LittleDry() throws LittleDryException { // a tester aussi
        if(!littleDry){
            for(int i=0;i<players.size();i++){
                int cpt_atout = 0;
                for(int j=0;j<players.get(i).getCards().size();j++){ // on calcule le nombre d'atouts
                    if(players.get(i).getCards().get(j).getColor() == TypeCard.Trump){
                        cpt_atout += players.get(i).getCards().get(j).getNumero();
                    }
                }
                if(cpt_atout == 1){ // si il a qu'un atout
                    littleDry = true;
                }
            }
        }
        if(littleDry){
            throw new LittleDryException();
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<CardModel> getDog() {
        return this.dog;
    }

    public ArrayList<CardModel> getGap() {
        return gap;
    }

    public ArrayList<CardModel> getCardsDeck() {
        return cardsDeck;
    }

    public Notification getNotif() {
        return notif;
    }

    public int getIdPlayerDistrib() {
        return idPlayerDistrib;
    }
}
