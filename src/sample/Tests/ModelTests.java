package sample.Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sample.Enumeration.TypeCard;
import sample.Exception.LittleDryException;
import sample.Model.CardModel;
import sample.Model.Model;

/**
 * Created by theo on 09/12/16.
 */
public class ModelTests {
    private Model model = new Model();

    @Before
    public void initialise(){
    }

    @Test
    public void testDeck(){
        Assert.assertEquals(model.getCardsDeck().size(),78);
    }

    @Test
    public void testAddCardHand(){
        int deckSize = model.getCardsDeck().size();
        model.addCardHand();
        Assert.assertEquals(model.getCardsDeck().size(), deckSize-1);
        Assert.assertEquals(model.getPlayers().get(0).getCards().size(), 1);
        model.addCardHand();
        Assert.assertEquals(model.getCardsDeck().size(), deckSize-2);
        Assert.assertEquals(model.getPlayers().get(0).getCards().size(), 2);
    }

    @Test
    public void testDistribution(){
        model.distribution();
        for (int i = 0; i < model.getPlayers().size(); i++) {
            Assert.assertEquals(model.getPlayers().get(i).getCards().size(), 3);
        }
        Assert.assertEquals(model.getDog().size(), 1);
    }

    @Test
    public void testAddCardDog(){
        int deckSize = model.getCardsDeck().size();
        model.addCardDog();
        Assert.assertEquals(model.getCardsDeck().size(), deckSize-1);
        Assert.assertEquals(model.getDog().size(), 1);
        Assert.assertTrue(model.getDog().get(0).isInDog());
    }

    @Test
    public void testDogToHand(){
        for (int i = 0; i < 6; i++) {
            model.addCardDog();
        }
        Assert.assertEquals(model.getDog().size(), 6);
        model.dogToHand();
        Assert.assertEquals(model.getPlayers().get(0).getCards().size(), 6);
        Assert.assertEquals(model.getDog().size(), 0);
    }

    @Test
    public void testSortHand(){
        for (int i = 0; i < 6; i++) {
            model.distribution();
        }
        model.sortHand();
        for (int i = 1; i < model.getPlayers().get(0).getCards().size(); i++){
            if(model.getPlayers().get(0).getCards().get(i-1).getColor() == model.getPlayers().get(0).getCards().get(i).getColor()){
                Assert.assertTrue(model.getPlayers().get(0).getCards().get(i-1).getNumero()
                        < model.getPlayers().get(0).getCards().get(i).getNumero());
            }
        }
    }
}
