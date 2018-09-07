package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerUp {
    private final String PU1_IMAGE = "brick4.gif";
    private final int FALL_SPEED = 20;
    private Image PU1;
    private ImageView myView;



    public PowerUp(int strength) {
        PU1 = new Image(this.getClass().getClassLoader().getResourceAsStream(PU1_IMAGE));
        myView = new ImageView(PU1);

    }

    public void pickedUp(Paddle targetPaddle) {
        targetPaddle.setPaddleWidth(targetPaddle.getPaddleWidth() * 2);
    }

    public void onHit(ImageView obj, double elapsed) {
        if (obj.getBoundsInParent().intersects(myView.getBoundsInParent())) {
            myView.setY(myView.getY() + FALL_SPEED * elapsed);

        }

    }
    public ImageView getView () {
        return myView;
    }

}
