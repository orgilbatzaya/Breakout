package game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle {
    private final String PADDLE_IMAGE = "paddle.gif";
    private final int PADDLE_SPEED = 45;
    private double paddleWidth;
    private Image paddle;
    private ImageView myView;

    public Paddle(int screenWidth, int screenHeight){
        paddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myView = new ImageView(paddle);
        paddleWidth = myView.getBoundsInLocal().getWidth();
        myView.setX(screenWidth / 2 - paddleWidth / 2);
        myView.setY(screenHeight / 2 - myView.getBoundsInLocal().getHeight() / 2);
    }

    public ImageView getView(){
        return myView;
    }
    public int getSpeed(){
        return PADDLE_SPEED;
    }
    public void setPaddleWidth(double width){

    }
    public double getPaddleWidth(){
        return paddleWidth;
    }




}
