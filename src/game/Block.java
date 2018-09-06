package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Block {
    private int myStrength;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    private final String BLOCK1_IMAGE = "brick1.gif";
    private final String BLOCK2_IMAGE = "brick2.gif";

    private ImageView myView;

    public Block(int strength){
        Image imageBrick = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK1_IMAGE));

        myStrength = strength;
        if(myStrength == 2){
            imageBrick = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK2_IMAGE));

        }
        myView = new ImageView(imageBrick);

    }

    public void onHit(ImageView obj){
        Image imageBrick;
        if(obj.getBoundsInParent().intersects(myView.getBoundsInParent()) && myStrength == 0){
            myView.setImage(null);
        } else if (obj.getBoundsInParent().intersects(myView.getBoundsInParent()) && myStrength > 0){
            myStrength -= 1;
                imageBrick = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK1_IMAGE));

            myView.setImage(imageBrick);
            System.out.println(myStrength);
        }
    }
    public ImageView getView () {
        return myView;
    }

}
