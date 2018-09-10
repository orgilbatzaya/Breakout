package game;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Stack;
import java.util.ArrayList;

public class Paddle {
    private int screenWidth;
    private int screenHeight;
    private final String PADDLE_IMAGE = "paddle.gif";
    private final int PADDLE_SPEED = 45;
    private final int PROJECTILE_SPEED = 100;
    private double paddleWidth;
    private Image paddle;
    private ImageView myView;
    private int livesLeft;
    private Stack<Projectile> myProjectiles;
    private ArrayList<Projectile> firedProjectiles;

    public Paddle(int width, int height){
        paddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myView = new ImageView(paddle);
        paddleWidth = myView.getBoundsInLocal().getWidth();
        screenWidth = width;
        screenHeight = height;
        myView.setX(screenWidth / 2 - paddleWidth / 2);
        myView.setY(140+(screenHeight / 2 - myView.getBoundsInLocal().getHeight() / 2));
        livesLeft = 3;
        myProjectiles = new Stack<>();
    }

    public ImageView getView(){
        return myView;
    }

    public void subtractLives(){
        livesLeft --;
    }
    public int getLivesLeft(){
        return livesLeft;
    }
    public void addLives(int add) {
        livesLeft += add;
    }

    public boolean checkLives(Timeline animation){
        if(livesLeft == 0){
            animation.stop();
            return false;
        }
        return true;
    }
    public int getSpeed(){
        return PADDLE_SPEED;
    }
    public void setPaddleWidth(double width){
        paddleWidth = width;

    }
    public double getPaddleWidth(){
        return paddleWidth;
    }
    public void addProjectile(Projectile proj){
        myProjectiles.push(proj);
    }
    public Stack<Projectile> getProjectiles(){
        return myProjectiles;
    }

    public void shoot(){
        Projectile proj = myProjectiles.pop();
        ImageView projIV = proj.getView();
        projIV.setX(myView.getX());
        projIV.setY(myView.getY());
        firedProjectiles.add(proj);

    }
    public ArrayList<Projectile> getFiredProjectiles(){
        return firedProjectiles;
    }




}
