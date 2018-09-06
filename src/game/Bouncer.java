package game;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Bouncer {
    private ImageView myView;
    private int BOUNCER_SPEED = 100;
    private int xdirection = 1;
    private int ydirection = 1;



    public Bouncer (Image image, int screenWidth, int screenHeight) {
        myView = new ImageView(image);
        // make sure it stays a circle
        // make sure it stays within the bounds

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

    public void move (double elapsed) {
        myView.setX(myView.getX() + xdirection * BOUNCER_SPEED * elapsed);
        myView.setY(myView.getY() + ydirection * BOUNCER_SPEED * elapsed);
    }





    public ImageView getView () {
        return myView;
    }


}
