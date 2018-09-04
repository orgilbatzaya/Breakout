package game;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Bouncer {
    //private final String BOUNCER_IMAGE = "ball.gif";
    private ImageView myView;
    private int BOUNCER_SPEED = 50;
    private Point2D myVelocity;
    private int direction = 1;



    public Bouncer (Image image, int screenWidth, int screenHeight) {
        myView = new ImageView(image);
        // make sure it stays a circle
        // make sure it stays within the bounds

        myView.setX(40 + screenWidth / 2 - myView.getBoundsInLocal().getWidth() / 2);
        myView.setY(screenHeight / 2 - myView.getBoundsInLocal().getHeight() / 2);
        // turn speed into velocity that can be updated on bounces
        myVelocity = new Point2D(myView.getX(), myView.getY());
                //getRandomInRange(BOUNCER_MIN_SPEED, BOUNCER_MAX_SPEED));
    }



    public void move (double elapsed, double screenWidth, double screenHeight, ImageView obj) {
        // collide all bouncers against the walls
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()
            || myView.getY() < 0 || myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()) {
            direction  *= -1;
        }
        else if (obj.getBoundsInParent().intersects(myView.getBoundsInParent())){
            direction *= -1;
        }
        myView.setX(myView.getX() + direction * BOUNCER_SPEED * elapsed);
        myView.setY(myView.getY() + direction * BOUNCER_SPEED * elapsed);
        System.out.println(myView.getY());
    }




    public ImageView getView () {
        return myView;
    }


}
