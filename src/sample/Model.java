package sample;

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
            cardsDeck.add(new CardModel(i,TypeCard.Trump));
        }
        for (int i = 1; i <= 14; i++){
            cardsDeck.add(new CardModel(i,TypeCard.Diamond));
            cardsDeck.add(new CardModel(i,TypeCard.Heart));
            cardsDeck.add(new CardModel(i,TypeCard.Club));
            cardsDeck.add(new CardModel(i,TypeCard.Spade));
        }
        cardsDeck.add(new CardModel(0,TypeCard.Excuse)); // L'excuse
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
        //players.get(0).getCards().removeAll(players.get(0).getCards()); // utiliser clear?

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

        /* Vide les 5 decks de couleurs */
        /*handSpade.removeAll(handSpade);
        handHeart.removeAll(handHeart);
        handTrumps.removeAll(handTrumps);
        handDiamond.removeAll(handDiamond);
        handClubs.removeAll(handClubs);*/
        handSpade.clear();
        handDiamond.clear();
        handTrumps.clear();
        handClubs.clear();
        handHeart.clear();
    }

    /*public void sortDog(){
        ArrayList<Card> handPique = new ArrayList();
        ArrayList<Card> handCoeur = new ArrayList();
        ArrayList<Card> handCarreau = new ArrayList();
        ArrayList<Card> handTrefle = new ArrayList();
        ArrayList<Card> handAtout = new ArrayList();

         On effectue la meme procédure avec le Chien

        for (int i = 0; i < this.getDog().size(); i++) {
            if (this.getDog().get(i).getFront().getColor() == TypeCard.Pique) {
                handPique.add(this.getDog().get(i));
            } else if (this.getDog().get(i).getFront().getColor() == TypeCard.Coeur) {
                handCoeur.add(this.getDog().get(i));
            } else if (this.getDog().get(i).getFront().getColor() == TypeCard.Atout) {
                handAtout.add(this.getDog().get(i));
            } else if (this.getDog().get(i).getFront().getColor() == TypeCard.Carreau) {
                handCarreau.add(this.getDog().get(i));
            } else if (this.getDog().get(i).getFront().getColor() == TypeCard.Trefle) {
                handTrefle.add(this.getDog().get(i));
            }
        }

        this.getDog().removeAll(this.getDog());

        Collections.sort(handPique);
        Collections.sort(handCoeur);
        Collections.sort(handAtout);
        Collections.sort(handCarreau);
        Collections.sort(handTrefle);

        for (int i = 0; i < handPique.size(); i++){
            this.getDog().add(handPique.get(i));
            view.updateAddCurrentPlayer(handPique.get(i), false);
        }
        for (int i = 0; i < handCoeur.size(); i++){
            this.getDog().add(handCoeur.get(i));
            view.updateAddCurrentPlayer(handCoeur.get(i), false);
        }
        for (int i = 0; i < handAtout.size(); i++){
            this.getDog().add(handAtout.get(i));
            view.updateAddCurrentPlayer(handAtout.get(i), false);
        }
        for (int i = 0; i < handCarreau.size(); i++){
            this.getDog().add(handCarreau.get(i));
            view.updateAddCurrentPlayer(handCarreau.get(i), false);
        }
        for (int i = 0; i < handTrefle.size(); i++){
            this.getDog().add(handTrefle.get(i));
            view.updateAddCurrentPlayer(handTrefle.get(i), false);
        }

        handPique.removeAll(handPique);
        handCoeur.removeAll(handCoeur);
        handAtout.removeAll(handAtout);
        handCarreau.removeAll(handCarreau);
        handTrefle.removeAll(handTrefle);
    }*/

    public void testLittleDry(){ // a tester aussi, je pense pas que ca marche
        if(!littleDry){
            for(int i=0;i<players.size();i++){
                int cpt_atout = 0;
                CardModel c = new CardModel();
                for(int j=0;j<players.get(i).getCards().size();j++){ // on calcule le nombre d'atouts
                    if(players.get(i).getCards().get(j).getColor() == TypeCard.Trump){
                        cpt_atout++;
                        c = players.get(i).getCards().get(j);
                    }
                }
                if(cpt_atout == 1){ // si il a qu'un atout
                    if(c.getNumero() == 1){
                        littleDry = true;
                    }
                }
            }
        }
        if(littleDry){
            System.out.println("PETIT SEC"); // a modifier
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
