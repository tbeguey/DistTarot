package sample.View;

import javafx.animation.*;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import sample.Model.CardModel;

import java.util.ArrayList;
import java.util.Collection;

public class Card extends Group {
    private CardModel cardModel;
    private ImageView front;
    private ImageView back = new ImageView();
    private static Image image_cached = new Image("file:./ressources-100/cache.jpg");
    private static long MoveDuration = 3000;
    private static long GardMoveDuration = 6000;
    private boolean isFlipped;

    public Card(ImageView front, CardModel cardModel, int x, int y)
    {
        this.cardModel = cardModel;
        this.front = front;
        this.front.setX(x);
        this.front.setY(y);
        this.back.setX(x);
        this.back.setY(y);
        this.back.setImage(image_cached);
        this.back.setFitWidth(80);
        this.back.setFitHeight(170);
        this.front.setFitWidth(80);
        this.front.setFitHeight(170);
        this.getChildren().add(front);
        this.getChildren().add(back);
        this.isFlipped = false;
        this.setOnMouseClicked(event -> {
            if(!cardModel.isInDog())
                this.flip();

        });
    }

    /* Retourne la carte */
    public void flip() {
        //front.setOpacity(0);
        this.isFlipped = !this.isFlipped;
        final RotateTransition rotateInBack = new RotateTransition(Duration.millis(1000), back);
        rotateInBack.setInterpolator(Interpolator.LINEAR);
        rotateInBack.setAxis(new Point3D(0,1,0));
        rotateInBack.setFromAngle(0);
        rotateInBack.setToAngle(180);

        final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(1000), front);
        rotateOutFront.setInterpolator(Interpolator.LINEAR);
        rotateOutFront.setAxis(new Point3D(0,1,0));
        rotateOutFront.setFromAngle(80);
        rotateOutFront.setToAngle(360);


        rotateInBack.play();
        rotateInBack.setOnFinished(event -> { rotateOutFront.play();back.setOpacity(0);front.setOpacity(1);});
        rotateInBack.setOnFinished(event -> { rotateOutFront.play();front.setOpacity(1);back.setOpacity(0);});



    }

    public void flipAlrdyReturned()
    {
        front.setOpacity(0);
        this.isFlipped = !this.isFlipped;
        final RotateTransition rotateInBack = new RotateTransition(Duration.millis(1000), back);
        rotateInBack.setInterpolator(Interpolator.LINEAR);
        rotateInBack.setAxis(new Point3D(0,1,0));
        rotateInBack.setFromAngle(0);
        rotateInBack.setToAngle(90);


        final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(1000), front);
        rotateOutFront.setInterpolator(Interpolator.LINEAR);
        rotateOutFront.setAxis(new Point3D(0,1,0));
        rotateOutFront.setFromAngle(0);
        rotateOutFront.setToAngle(180);


        rotateOutFront.play();
        rotateOutFront.setOnFinished(event -> { rotateOutFront.play();back.setOpacity(0);front.setOpacity(1);});
        rotateOutFront.setOnFinished(event -> {back.setOpacity(0);});
    }

    /* Effectue un déplacement de translation de la carte */
    public void move(int posX, int posY, int idJoueur){
        TranslateTransition translateTransitionFront = new TranslateTransition(Duration.millis(MoveDuration), front);
        translateTransitionFront.setToX(posX);
        translateTransitionFront.setToY(posY);

        TranslateTransition translateTransitionBack = new TranslateTransition(Duration.millis(MoveDuration), back);
        translateTransitionBack.setToX(posX);
        translateTransitionBack.setToY(posY);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransitionFront, translateTransitionBack);

        /* Fait disparaitre les cartes des autres joueurs */
        if(idJoueur > 1){
            parallelTransition.setOnFinished(event -> {
                front.setOpacity(0);
                back.setOpacity(0);
            });
        }

        parallelTransition.play();
    }

    /* Déplace les cartes du chien (action de la Garde) */
    public void moveGard(){
        TranslateTransition translateTransitionFrontX = new TranslateTransition(Duration.millis(GardMoveDuration), front);
        translateTransitionFrontX.setToX(1500);
        TranslateTransition translateTransitionBackX = new TranslateTransition(Duration.millis(GardMoveDuration), back);
        translateTransitionBackX.setToX(1500);

        ParallelTransition parallelTransitionX = new ParallelTransition(translateTransitionFrontX, translateTransitionBackX);

        TranslateTransition translateTransitionFrontY = new TranslateTransition(Duration.millis(500), front);
        translateTransitionFrontY.setToY(1500);
        TranslateTransition translateTransitionBackY = new TranslateTransition(Duration.millis(500), back);
        translateTransitionBackY.setToY(1500);

        ParallelTransition parallelTransitionY = new ParallelTransition(translateTransitionFrontY, translateTransitionBackY);

        new SequentialTransition(parallelTransitionX, parallelTransitionY).play();
    }

    public CardModel getCardModel() {
        return cardModel;
    }
    public ImageView getBack(){return back;}
}
