package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Block {
    private int myStrength;
    private final String BLOCK1_IMAGE = "brick1.gif";
    private final String BLOCK2_IMAGE = "brick2.gif";
    private Image weakBrick;
    private Image strongBrick;
    protected ImageView myView;

    public Block(int strength){
        myStrength = strength;
        weakBrick = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK1_IMAGE));
        strongBrick = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK2_IMAGE));
        myView = new ImageView(weakBrick);
        if(myStrength == 2){
            myView.setImage(strongBrick);
        }

    }

    public void onHit(ImageView obj){
        Image imageBrick;
        if(obj.getBoundsInParent().intersects(myView.getBoundsInParent()) && myStrength == 0){
            myView.setImage(null);
        } else if (obj.getBoundsInParent().intersects(myView.getBoundsInParent()) && myStrength > 0){
            myStrength -= 1;
            myView.setImage(weakBrick);
            System.out.println(myStrength);
        }
    }
    public ImageView getView () {
        return myView;
    }

}
