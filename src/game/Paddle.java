package game;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle {
    private final String PADDLE_IMAGE = "paddle.gif";
    private final int PADDLE_SPEED = 45;
    private double paddleWidth;
    private Image paddle;
    private ImageView myView;
    private int livesLeft;

    public Paddle(int screenWidth, int screenHeight){
        paddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myView = new ImageView(paddle);
        paddleWidth = myView.getBoundsInLocal().getWidth();
        myView.setX(screenWidth / 2 - paddleWidth / 2);
        myView.setY(140+(screenHeight / 2 - myView.getBoundsInLocal().getHeight() / 2));
        livesLeft = 3;
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




}
