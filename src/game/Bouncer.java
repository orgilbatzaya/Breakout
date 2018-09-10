package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Bouncer {
    private ImageView myView;
    private final String BOUNCER_IMAGE = "ball.gif";
    private int BOUNCER_SPEED = 60;
    private int xdirection = 1;
    private int ydirection = 1;
    private int screenWidth;
    private int screenHeight;



    public Bouncer (int screenWidth, int screenHeight) {
        Image bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myView = new ImageView(bouncerImage);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        setPos();
    }


    public void checkDirection (ImageView obj, Paddle target) {
        // collide all bouncers against the walls
        checkScreenBounds(target);
        if (obj.getBoundsInParent().intersects(myView.getBoundsInParent())) {
            ydirection *= -1;
        }
    }

    public void move (double elapsed, Paddle target, boolean start) {
        if(start) {
            myView.setX(myView.getX() + xdirection * BOUNCER_SPEED * elapsed);
            myView.setY(myView.getY() + ydirection * BOUNCER_SPEED * elapsed);
            checkScreenBounds(target);
            myView.setX(myView.getX() + xdirection * BOUNCER_SPEED * elapsed);
            myView.setY(myView.getY() + ydirection * BOUNCER_SPEED * elapsed);

        }
    }
    private void checkScreenBounds(Paddle targetPaddle){
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()){
            xdirection  *= -1;
        }
        else if (myView.getY() < 0 ){
            ydirection *= -1;
        }
        else if (myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()){
            setPos();
            targetPaddle.subtractLives();

        }
    }
    public void setPos(){
        myView.setX(screenWidth / 2 - myView.getBoundsInLocal().getWidth() / 2);
        myView.setY(120 + screenHeight / 2 - myView.getBoundsInLocal().getHeight() / 2);
    }

    public void increaseSpeed(double mult){
        BOUNCER_SPEED *= mult;
    }


    public ImageView getView () {
        return myView;
    }

}
