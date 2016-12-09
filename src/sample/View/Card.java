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
    private int x;
    private int y;
    private static Image image_cached = new Image("file:./ressources-100/cache.jpg");
    private static long halfFlipDuration = 1000;
    private static long MoveDuration = 3000;
    private static long GardMoveDuration = 6000;
    private boolean isFlipped;

    public Card(ImageView front, CardModel cardModel, int x, int y)
    {
        this.cardModel = cardModel;
        this.front = front;
        this.x = x;
        this.y = y;
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

/*    void flip() {
        RotateTransition rotateB = new RotateTransition(Duration.millis(3000), back);
        rotateB.setAxis(Rotate.Y_AXIS);
        rotateB.setInterpolator(Interpolator.LINEAR);
        rotateB.setFromAngle(0);
        rotateB.setToAngle(-180);


        RotateTransition rotateF = new RotateTransition(Duration.millis(1000), front);
        rotateF.setAxis(Rotate.Y_AXIS);
        rotateF.setInterpolator(Interpolator.LINEAR);
        rotateF.setFromAngle(0);

        rotateF.setToAngle(90);
        rotateF.setToAngle(180);

        SequentialTransition st = new SequentialTransition(rotateB,rotateF);
        st.play();
    }*/

    Collection<Node> getNodes(){
        ArrayList<Node> al = new ArrayList<>();
        al.add(front);
        al.add(back);
        return al;
    }

    public void flip() {
        back.setOpacity(1);
        this.isFlipped = !this.isFlipped;
        final RotateTransition rotateInBack = new RotateTransition(Duration.millis(1000), back);
        rotateInBack.setInterpolator(Interpolator.LINEAR);
        rotateInBack.setAxis(new Point3D(0,1,0));
        rotateInBack.setFromAngle(0);
        rotateInBack.setToAngle(180);


        final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(1000), front);
        rotateOutFront.setInterpolator(Interpolator.LINEAR);
        rotateOutFront.setAxis(new Point3D(0,-1,0));
        rotateOutFront.setFromAngle(0);
        rotateOutFront.setToAngle(360);

        front.setOpacity(0);

        rotateInBack.play();
        rotateInBack.setOnFinished(event -> { rotateOutFront.play();back.setOpacity(0);front.setOpacity(1);});

    }

    public void move(int posX, int posY, int idJoueur){
        TranslateTransition translateTransitionFront = new TranslateTransition(Duration.millis(MoveDuration), front);
        translateTransitionFront.setToX(posX);
        translateTransitionFront.setToY(posY);

        TranslateTransition translateTransitionBack = new TranslateTransition(Duration.millis(MoveDuration), back);
        translateTransitionBack.setToX(posX);
        translateTransitionBack.setToY(posY);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransitionFront, translateTransitionBack);

        if(idJoueur > 1){
            parallelTransition.setOnFinished(event -> {
                front.setOpacity(0);
                back.setOpacity(0);
            });
        }

        parallelTransition.play();
    }

    public void moveGard(){
        TranslateTransition translateTransitionFrontX = new TranslateTransition(Duration.millis(GardMoveDuration), front);
        translateTransitionFrontX.setToX(500);
        TranslateTransition translateTransitionBackX = new TranslateTransition(Duration.millis(GardMoveDuration), back);
        translateTransitionBackX.setToX(500);

        ParallelTransition parallelTransitionX = new ParallelTransition(translateTransitionFrontX, translateTransitionBackX);

        TranslateTransition translateTransitionFrontY = new TranslateTransition(Duration.millis(500), front);
        translateTransitionFrontY.setToY(500);
        TranslateTransition translateTransitionBackY = new TranslateTransition(Duration.millis(500), back);
        translateTransitionBackY.setToY(500);

        ParallelTransition parallelTransitionY = new ParallelTransition(translateTransitionFrontY, translateTransitionBackY);

        new SequentialTransition(parallelTransitionX, parallelTransitionY).play();
    }

    public CardModel getCardModel() {
        return cardModel;
    }
}
