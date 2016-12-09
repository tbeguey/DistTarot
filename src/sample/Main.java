package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Model.Model;
import sample.View.View;
import sample.Controller;


public class Main
        extends Application
{
    private Model model;
    private View view;
     private Controller controller;

    public void start(Stage primaryStage)

    {
        this.model = new Model();
        this.view = new View(this.model);
        model.addObserver(view);
        this.controller = new Controller(this.model,this.view);
        view.updateDeck();

        view.getReplay().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            view.getWindow().close();
                start(new Stage());
            }
        });
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
