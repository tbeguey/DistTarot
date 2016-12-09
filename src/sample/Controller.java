package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Exception.LittleDryException;
import sample.Model.Model;
import sample.View.Card;
import sample.View.View;

public class Controller
{
    private View view;
    private Model model;

    public Controller(Model m, View v)
    {
        this.view = v;
        this.model = m;

        view.getDistribution().setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                for(int i=0;i<6;i++){
                    model.distribution();
                }
                view.getRoot().getChildren().add(view.getReturnedAll());
                view.getRoot().getChildren().remove(view.getDistribution());
            }
        });

        view.getReturnedAll().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.returnedAllCard();

                view.getRoot().getChildren().add(view.getSort());
                view.getRoot().getChildren().remove(view.getReturnedAll());
            }
        });

        view.getSort().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.setCardPlaced(0);
                if (model.getPlayers().get(0).getCards().size() + model.getDog().size() == 24) {
                    view.setPositionCardX(150);
                    view.setPositionCardY(50);
                    view.setPositionDogX(350);
                    view.setPositionDogY(700);
                    model.sortHand();
                    try {
                        model.LittleDry(); // on test le petit sec une fois que toutes les cartes sont distribuées et triées
                    } catch (LittleDryException e) {
                        e.printStackTrace();
                    }
                }
                view.getRoot().getChildren().add(view.getTake());
                view.getRoot().getChildren().add(view.getGard());
                view.getRoot().getChildren().remove(view.getSort());
            }
        });

        view.getTake().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.setCardPlaced(0);
                for (int i = 0; i < model.getDog().size(); i++) {
                    Card c = view.whichCardView(model.getDog().get(i));
                    c.flip();
                }
                model.dogToHand();
                view.setPositionCardX(150);
                view.setPositionCardY(50);
                view.setPositionDogX(350);
                view.setPositionDogY(700);
                model.sortHand();
                view.changeActionToRemoveCard();
            }
        });

        view.getGard().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < model.getDog().size(); i++) {
                    Card c = view.whichCardView(model.getDog().get(i));
                    c.flip();
                    c.moveGard();
                }
            }
        });

        view.getReplay().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                view.getWindow().close();

                new Model();
                new View(model);
                model.initialiseCardsDeck();

               // new Controller();
                view.updateDeck();
            }
        });


    }
}
