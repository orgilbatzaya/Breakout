package game;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Bouncer {
    private ImageView myView;
    private final String BOUNCER_IMAGE = "ball.gif";
    private int BOUNCER_SPEED = 60;
    private int xdirection = 1;
    private int ydirection = 1;



    public Bouncer (int screenWidth, int screenHeight) {
        Image bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myView = new ImageView(bouncerImage);

        myView.setX(screenWidth / 2 - myView.getBoundsInLocal().getWidth() / 2);
        myView.setY(screenHeight / 2 - myView.getBoundsInLocal().getHeight() / 2);
        // turn speed into velocity that can be updated on bounces
        //myVelocity = new Point2D(myView.getX(), myView.getY());
                //getRandomInRange(BOUNCER_MIN_SPEED, BOUNCER_MAX_SPEED));
    }



    public void checkDirection (double screenWidth, double screenHeight, ImageView obj) {
        // collide all bouncers against the walls
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()){
            xdirection  *= -1;
        }
        else if (myView.getY() < 0 || myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()){
            ydirection *= -1;
        }
        else if (obj.getBoundsInParent().intersects(myView.getBoundsInParent())) {
            ydirection *= -1;
        }
    }
    public void paddleBounce(){
        ydirection = 1;
    }

    public void move (double screenWidth, double screenHeight, double elapsed) {
        myView.setX(myView.getX() + xdirection * BOUNCER_SPEED * elapsed);
        myView.setY(myView.getY() + ydirection * BOUNCER_SPEED * elapsed);
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()){
            xdirection  *= -1;
        }
        else if (myView.getY() < 0 || myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()){
            ydirection *= -1;
        }
        myView.setX(myView.getX() + xdirection * BOUNCER_SPEED * elapsed);
        myView.setY(myView.getY() + ydirection * BOUNCER_SPEED * elapsed);
        //System.out.println(myView.getY());
    }





    public ImageView getView () {
        return myView;
    }


}
