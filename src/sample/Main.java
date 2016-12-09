package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main
        extends Application
{
    private Model model;
    private View view;
    private Controller controller;

    public void start(Stage primaryStage)
            throws Exception
    {
        this.model = new Model();
        this.view = new View(this.model);
        model.addObserver(view);
        this.controller = new Controller(this.model, this.view);
        view.updateDeck();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
